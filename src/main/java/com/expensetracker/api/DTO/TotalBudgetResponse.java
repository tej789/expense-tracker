package com.expensetracker.api.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.Month;
import java.time.Year;

@Getter
@Setter
public class TotalBudgetResponse {

    private Month month;
    private Year year;
    private double totalBudget;

}
