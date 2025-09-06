package org.pouryakr.customer;

import org.pouryakr.clients.FraudCheckResponse;
import org.pouryakr.clients.FraudClient;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(
        CustomerRepository repository,
        FraudClient fraudClient
) {

    public void registerCustomer(CustomerRegistrationRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();

        repository.saveAndFlush(customer);

        FraudCheckResponse fraudster = fraudClient.isFraudster(customer.getId());

        if (fraudster.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
    }
}
