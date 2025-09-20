package com.github.chat_ai.service.impl;

import com.github.chat_ai.service.DocIngestionService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocIngestionServiceImpl implements DocIngestionService {
    private final ResourceLoader resourceLoader;
    private final EmbeddingStoreIngestor embeddingStoreIngestor;

    @Override
    @Async
    public void storeDocument(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource(filePath);
        Document document = loadDocument(Path.of("/",
                resource.getFile().toPath().toString()), getParserForDocument(resource));
       embeddingStoreIngestor.ingest(document);
        CompletableFuture.completedFuture(filePath);
    }

    private DocumentParser getParserForDocument(Resource resource) throws IOException {
        return resource.getFile().toPath().toString().endsWith(".pdf") ?
                new ApachePdfBoxDocumentParser() : new TextDocumentParser();
    }
}
