package org.pouryakr.fraud;

import lombok.extern.slf4j.Slf4j;
import org.pouryakr.clients.FraudCheckResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@Slf4j
public record FraudController(FraudCheckHistoryService service) {

    @GetMapping("{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable Integer customerId) {
        boolean fraudulentCustomer = service.isFraudulentCustomer(customerId);
        log.info("fraud check request for customer {}", customerId);
        return new FraudCheckResponse(fraudulentCustomer);
    }
}
