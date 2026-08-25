package com.expensetracker.api.controller;


import com.expensetracker.api.DTO.BudgetRequest;
import com.expensetracker.api.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService ){
        this.budgetService = budgetService;
    }


    @PostMapping("setBudget")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> setBudget(@RequestBody BudgetRequest request){
        String msg = budgetService.setBudget(request);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }


}
