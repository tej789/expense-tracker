package com.expensetracker.api.DTO;

import com.expensetracker.api.model.Transaction;

public interface TransactionValidationHandler {

    void setNext(TransactionValidationHandler next);

    void validate(TransactionRequest request);
}
