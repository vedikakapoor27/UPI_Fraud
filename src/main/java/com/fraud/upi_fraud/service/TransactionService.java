package com.fraud.upi_fraud.service;

import com.fraud.upi_fraud.engine.RiskEvaluator;
import com.fraud.upi_fraud.model.RiskResult;
import com.fraud.upi_fraud.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private RiskEvaluator riskEvaluator;

    public RiskResult process(Transaction transaction) {
        return riskEvaluator.evaluate(transaction);
    }
}