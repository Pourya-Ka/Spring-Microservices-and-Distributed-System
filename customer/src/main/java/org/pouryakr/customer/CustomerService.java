package org.pouryakr.customer;

import org.pouryakr.amqp.RabbitMQMessageProducer;
import org.pouryakr.clients.FraudCheckResponse;
import org.pouryakr.clients.FraudClient;
import org.pouryakr.notification.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(
        CustomerRepository repository,
        FraudClient fraudClient,
        RabbitMQMessageProducer rabbitMQMessageProducer
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

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s welcome to HRK Academy...", customer.getFirstName())
        );
        rabbitMQMessageProducer.publish(notificationRequest, "internal.exchange", "internal.notification.routing-key");

    }
}
