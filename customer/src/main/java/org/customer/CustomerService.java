package org.customer;

import org.springframework.stereotype.Service;

@Service
public record CustomerService(
        CustomerRepository repository
) {

    public void registerCustomer(CustomerRegistrationRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();

        repository.saveAndFlush(customer);

        // todo : check if fraudster
    }
}
