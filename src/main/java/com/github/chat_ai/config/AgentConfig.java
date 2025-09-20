package com.github.chat_ai.config;
import com.github.chat_ai.service.CustomerServiceAgent;
import com.github.chat_ai.service.impl.CustomerService;
import com.github.chat_ai.service.impl.CustomerServiceTools;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;

import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever.*;


@Configuration
@RequiredArgsConstructor
public class AgentConfig {


    @Value("${spring.ai.openai.api-key}")
    private String keyOpenai;
    @Value("${spring.ai.openai.chat.options.model}")
    private  String model;

    @Value("${spring.ai.openai.base-url}")
    private  String url;

    @Bean
    public ChatLanguageModel chatLanguageModel() {
       return OpenAiChatModel.builder().apiKey(keyOpenai)
               .modelName(model).build();
    }


    @Bean
    public StreamingChatLanguageModel streamingChatLanguageModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(keyOpenai)
                .modelName(model)
                .baseUrl(url)
                .temperature(0.7)
                .maxTokens(2000)
                .logRequests(true)
                .logResponses(true)
                .build();
    }




    @Bean
    public EmbeddingModel embeddingModel() {
        return OpenAiEmbeddingModel.builder()
                .apiKey(keyOpenai)
                .modelName("text-embedding-3-large") // ou outro disponível
                .build();
    }




    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore<TextSegment> store,
                                             EmbeddingModel embeddingModel

    ) {
        return builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .maxResults(100)
                .minScore(0.7)
                .build();
    }
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }


    @Bean
    public CustomerServiceTools customerServiceTools() {
        return new CustomerServiceTools(new CustomerService());
    }

    @Bean
    public CustomerServiceAgent customerServiceAgent(
               ChatLanguageModel chatLanguageModel,
                StreamingChatLanguageModel streamingChatLanguageModel,
                ContentRetriever retriever,
                CustomerServiceTools tools) {

        return AiServices.builder(CustomerServiceAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .streamingChatLanguageModel(streamingChatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(tools)
               .contentRetriever(retriever)
                .build();
    }

}
