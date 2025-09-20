package com.github.chat_ai.service.impl;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CustomerServiceTools {

    private final CustomerService customerService;

    @Tool
    public List<Customer> findCustomer(String email, String correlationId) {
        return customerService.findCustomer(email, correlationId);
    }

    @Tool
    public void updateAddress(String correlationId, String address) {
        customerService.updateAddress(correlationId, address);
    }

    @Tool
    public List<Customer> retrieveAllApplications() {
        return customerService.retrieveAllApplications();
    }
}
