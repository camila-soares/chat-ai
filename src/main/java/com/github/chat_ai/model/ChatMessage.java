package com.github.chat_ai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data

@AllArgsConstructor
public class ChatMessage {

    private String author;
    private String text;
    private LocalDateTime timestamp;
}
