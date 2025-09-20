package com.github.chat_ai.service.impl;


import lombok.extern.slf4j.Slf4j;
import opennlp.tools.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Service
@Slf4j
public class CustomerService {

    private final Collection<Customer> customers = new ArrayList<>();

    public CustomerService() {
        customers.addAll(Arrays.asList(new Customer("18666A", "curtis@gmail.com", "Brock", "Curtis", "76 Chalmers Ave, Redfern 2119", "FLOODS", 2000, "SUBMITTED"), new Customer("19966A", "curtis@gmail.com", "Brock", "Curtis", "76 Chalmers Ave, Redfern 2119", "BUSH_FIRE", 550, "SUBMITTED"), new Customer("21966A", "curtis@gmail.com", "Brock", "Curtis", "76 Chalmers Ave, Redfern 2119", "HAIL_DAMAGE", 250, "SUBMITTED"), new Customer("36559B", "blogg@gmail.com", "Joe", "Blogg", "40 Bridge Road, Sydney, 2000", "BUSH_FIRE", 300, "APPROVED"), new Customer("91137D", "duran@gmail.com", "Leandro", "Duran", "199 George Street, Newtown, 2000", "HAIL_DAMAGE", 300, "PAYMENT_SUBMITTED")));

    }

    public List<Customer> findCustomer(String email, String correlationId) {
        if (email != null && !StringUtil.isEmpty(email)) {
            return getCustomerApplicationsByEmail(email);
        }
        return getCustomerApplicationsByCorrelationid(correlationId);
    }

    public void updateAddress(String correlationId, String address) {
        List<Customer> customerList = getCustomerApplicationsByCorrelationid(correlationId);
        Customer customer = customerList.getFirst();
        customers.remove(customer);
        customers.add(new Customer(correlationId, customer.correlationId(), customer.firstName(), customer.lastName(),
                address, customer.claimType(), customer.amount(), customer.status()));
    }

    public List<Customer> retrieveAllApplications() {
        return customers.stream().toList();
    }

    private List<Customer> getCustomerApplicationsByEmail(String email) {
        log.info("[{}]Finding customer with email:", email);
        var custList = customers.stream().filter(customer -> customer.email().equals(email)).toList();
        if (custList.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }
        return custList;
    }

    private List<Customer> getCustomerApplicationsByCorrelationid(String correlationId) {
        log.info("[{}]Finding customer with correlationId : ", correlationId);
        var custList = customers.stream().filter(customer -> customer.correlationId().equals(correlationId)).toList();
        if (custList.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }
        return custList.stream().filter(c -> c.correlationId().equals(correlationId)).toList();
    }
}
