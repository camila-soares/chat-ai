package com.github.chat_ai.service.impl;

import com.github.chat_ai.model.ChatMessage;
import com.github.chat_ai.service.Assistant;
import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class ChatService implements Assistant {

    private final ChatLanguageModel chatLanguageModel;
    private final List<ChatMessage> history = new CopyOnWriteArrayList<>();

    @Override
    public void add(ChatMessage msg) {
        chatLanguageModel.generate(msg.getText());
        history.add(msg);

    }

    @Override
    public List<ChatMessage> getAll() {
        return history;
    }
}
