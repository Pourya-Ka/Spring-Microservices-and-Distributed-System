package org.fraud;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record FraudCheckHistoryService(
        FraudCheckHistoryRepo repository
) {

    public boolean isFraudulentCustomer(Integer customerId) {
        repository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(false)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return false;
    }
}
