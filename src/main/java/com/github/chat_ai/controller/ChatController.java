package com.github.chat_ai.controller;


import com.github.chat_ai.model.ChatMessage;
import com.github.chat_ai.service.Assistant;
import com.github.chat_ai.service.CustomerServiceAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ChatController {

    private final CustomerServiceAgent agent;
    private final Assistant assistant;
    List<ChatMessage> messages = new ArrayList<>();


    @GetMapping("/form")
    public ModelAndView showForm() {
        return new ModelAndView("form");
    }

    @PostMapping("/ask")
    public String ask(@RequestParam("question") String question, Model model) {
        messages.add(new ChatMessage("User", question, LocalDateTime.now()));

        String reposta = agent.chat(question);
       messages.add(new ChatMessage("Assistant", reposta, LocalDateTime.now()));


       return reposta;
    }

}
