package com.expensetracker.api.service;

import com.expensetracker.api.DTO.MonthlySummaryResponse;
import com.expensetracker.api.model.CategoryType;
import java.time.Month;
import java.time.Year;
import java.util.List;

public interface MonthlySummaryService {

     MonthlySummaryResponse getSummaryOfCategory(
            Month month,
            Year year,
            CategoryType category
    );

     List<MonthlySummaryResponse> getMonthlySummary(
            Month month,
            Year year);

}

