package com.expensetracker.api.controller;


import com.expensetracker.api.DTO.BudgetRequest;
import com.expensetracker.api.DTO.BudgetResponse;
import com.expensetracker.api.Exception.Response;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.Year;
import java.util.List;

@RestController
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService ){
        this.budgetService = budgetService;
    }


    @PostMapping("/budget")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BudgetResponse> setBudget(@RequestBody BudgetRequest request){
        BudgetResponse b = budgetService.setBudget(request);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }

    @GetMapping("/budget")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BudgetResponse> getBudget(
            @RequestParam java.time.Month month,
            @RequestParam java.time.Year year,
            @RequestParam CategoryType category
            ){

        BudgetResponse b = budgetService.getBudget(month,year,category);

        return new ResponseEntity<>(b,HttpStatus.OK);

    }

    @GetMapping("/allbudgets")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BudgetResponse>> getAllBudgets(
            @RequestParam Month month,
            @RequestParam Year year
    ) {
        List<BudgetResponse> budgetList = budgetService.getAllBudgets(month, year);
        return new ResponseEntity<>(budgetList, HttpStatus.OK);
    }

    @PutMapping("/budget")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BudgetResponse> updateBudget(@RequestBody BudgetRequest request){

        BudgetResponse b = budgetService.updateBudget(request);
        return new ResponseEntity<>(b,HttpStatus.OK);
    }

    @DeleteMapping("/budget")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteBudget(
            @RequestParam Month month,@RequestParam Year year ,@RequestParam CategoryType category
    ){

        budgetService.deleteBudget(month,year,category);

        return new ResponseEntity<>("Budget Deleted Successfully",HttpStatus.OK);

    }

}
