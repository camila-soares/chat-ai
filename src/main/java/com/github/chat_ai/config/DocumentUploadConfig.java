package com.github.chat_ai.config;


import com.github.chat_ai.service.impl.DocIngestionServiceImpl;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;

import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4;

@Configuration
public class DocumentUploadConfig {
    @Bean
    public FileSystemResourceLoader resourceLoader() {
        return new FileSystemResourceLoader();
    }
    private DocumentParser getParserForDocument(Resource resource) throws IOException {
        return resource.getFile().toPath().toString().endsWith(".pdf") ? new ApachePdfBoxDocumentParser() : new TextDocumentParser();
    }

    private void loadEmbeddingForDocument(EmbeddingModel embeddingModel, Resource resource, EmbeddingStore<TextSegment> embeddingStore) throws IOException {
        Document document = loadDocument(resource.getFile().toPath(), getParserForDocument(resource));

        DocumentSplitter documentSplitter = DocumentSplitters.recursive(100, 0, new OpenAiTokenizer(GPT_4.name()));
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(documentSplitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        ingestor.ingest(document);
    }
    @Bean
    public EmbeddingStoreIngestor embeddingStoreIngestor(EmbeddingModel embeddingModel, EmbeddingStore<TextSegment> embeddingStore) {
        DocumentSplitter documentSplitter = DocumentSplitters.recursive(100, 0, new OpenAiTokenizer(GPT_4.name()));
        return EmbeddingStoreIngestor.builder()
                .documentSplitter(documentSplitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
    }

    @Bean
   public DocIngestionServiceImpl docIngestionService(EmbeddingStoreIngestor embeddingStoreIngestor, FileSystemResourceLoader resourceLoader) {
        return new DocIngestionServiceImpl(resourceLoader, embeddingStoreIngestor);
    }


}
