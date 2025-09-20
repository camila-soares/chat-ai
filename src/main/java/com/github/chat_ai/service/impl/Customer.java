package com.github.chat_ai.service.impl;




public record Customer(
        String correlationId,
        String email,
        String firstName,
        String lastName, String address, String claimType, Integer amount, String status) {
}
