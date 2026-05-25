package com.fraud.upi_fraud.controller;

import com.fraud.upi_fraud.model.RiskResult;
import com.fraud.upi_fraud.model.Transaction;
import com.fraud.upi_fraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/evaluate")
    public RiskResult evaluate(@RequestBody Transaction transaction) {
        return transactionService.process(transaction);
    }
}