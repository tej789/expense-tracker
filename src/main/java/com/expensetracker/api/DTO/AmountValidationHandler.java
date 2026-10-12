package com.expensetracker.api.DTO;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AmountValidationHandler implements TransactionValidationHandler{

    private TransactionValidationHandler next;

    @Override
    public void setNext(TransactionValidationHandler next) {
this.next = next;
    }

    @Override
    public void validate(TransactionRequest request) {
        BigDecimal max = new BigDecimal("9999999.99");


        if(request.getAmount().compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException(
                    "Transaction Amount Must Be Greater Then Zero"
            );
        }

        if(request.getAmount().compareTo(max)>0){
            throw new IllegalArgumentException(
                    "Transaction Amount Exceeds The Maximum Allowed Limit of " + max
            );
        }


        if(next!=null){
            next.validate(request);
        }

    }
}
