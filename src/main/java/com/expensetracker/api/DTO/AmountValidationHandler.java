package com.expensetracker.api.DTO;

import com.expensetracker.api.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class AmountValidationHandler implements TransactionValidationHandler{

    private TransactionValidationHandler next;


    @Override
    public void setNext(TransactionValidationHandler next) {
this.next = next;
    }

    @Override
    public void validate(TransactionRequest request) {
        if(request.getAmount()<=0){
            throw new IllegalArgumentException(
                    "Transaction Amount Must Be Greater Then One"
            );
        }


        if(next!=null){
            next.validate(request);
        }

    }
}
