package com.expensetracker.api.DTO;

import com.expensetracker.api.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateValidationHandler implements TransactionValidationHandler{
    private TransactionValidationHandler next;

    @Override
    public void setNext(TransactionValidationHandler next) {
        this.next = next;
    }

    @Override
    public void validate(TransactionRequest request) {

        if (request.getTransactionDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Transaction date cannot be in the future");
        }

        if(next!=null){
            next.validate(request);
        }

    }
}
