package com.expensetracker.api.controller;

import com.expensetracker.api.DTO.MonthlySummaryResponse;
import com.expensetracker.api.service.MonthlySummaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<MonthlySummaryResponse> getMonthlySummary(
            @RequestParam Month month,
            @RequestParam Year year
    ){
        return monthlySummaryService.getMonthlySummary(month, year);
    }

}
