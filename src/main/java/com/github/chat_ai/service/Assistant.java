package com.github.chat_ai.service;

import com.github.chat_ai.model.ChatMessage;
import dev.langchain4j.service.spring.AiService;
import java.util.List;

@AiService
public interface Assistant {

void add(ChatMessage chatMessage);

     List<ChatMessage> getAll();



}
