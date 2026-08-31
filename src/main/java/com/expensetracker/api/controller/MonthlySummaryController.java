package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.MonthlySummaryResponse;
import com.expensetracker.api.model.CategoryType;
import com.expensetracker.api.service.MonthlySummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.time.Year;
import java.util.List;

@RestController
public class MonthlySummaryController {

    private final MonthlySummaryService monthlySummaryService;

    public MonthlySummaryController(MonthlySummaryService monthlySummaryService) {
        this.monthlySummaryService = monthlySummaryService;
    }

    @GetMapping("/summary")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getMonthlySummary(
            @RequestParam Year year,
            @RequestParam Month month,
            @RequestParam(required = false) CategoryType category
    ) {
        if (category != null) {
            MonthlySummaryResponse SummaryCategory = monthlySummaryService.getSummaryOfCategory(month, year, category);
            return ResponseEntity.ok(SummaryCategory);
        }

        List<MonthlySummaryResponse> Summary = monthlySummaryService.getMonthlySummary(month, year);
        return ResponseEntity.ok(Summary);
    }


}
