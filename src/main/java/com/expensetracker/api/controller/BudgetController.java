package com.expensetracker.api.controller;


import com.expensetracker.api.DTO.BudgetRequest;
import com.expensetracker.api.Exception.Response;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService ){
        this.budgetService = budgetService;
    }


    @PostMapping("/budget")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BudgetRequest> setBudget(@RequestBody BudgetRequest request){
        BudgetRequest b = budgetService.setBudget(request);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }

    @GetMapping("/budget")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BudgetRequest> getBudget(
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam CategoryType category
            ){

        BudgetRequest b = budgetService.getBudget(month,year,category);

        return new ResponseEntity<>(b,HttpStatus.OK);

    }

    @GetMapping("/allbudgets")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BudgetRequest>> getAllBudgets(
            @RequestParam int month,
            @RequestParam int year
    ) {
        List<BudgetRequest> budgetList = budgetService.getAllBudgets(month, year);
        return new ResponseEntity<>(budgetList, HttpStatus.OK);
    }

    @PutMapping("/budget")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BudgetRequest> updateBudget(@RequestBody BudgetRequest request){

        BudgetRequest b = budgetService.updateBudget(request);
        return new ResponseEntity<>(b,HttpStatus.OK);
    }

    @DeleteMapping("/budget")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteBudget(
            @RequestParam int month,@RequestParam int year ,@RequestParam CategoryType category
    ){

        budgetService.deleteBudget(month,year,category);

        return new ResponseEntity<>("Budget Deleted Successfully",HttpStatus.OK);

    }

}
