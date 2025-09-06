package org.pouryakr.customer;


public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
){

}
