package com.expensetracker.api.DTO;

public interface TransactionValidationHandler {

    void setNext(TransactionValidationHandler next);

    void validate(TransactionRequest request);
}
