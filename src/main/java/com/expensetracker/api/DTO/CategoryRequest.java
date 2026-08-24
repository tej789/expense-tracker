package com.expensetracker.api.DTO;

import com.expensetracker.api.model.CategoryType;
import lombok.Data;

@Data
public class CategoryRequest {
    private CategoryType category;
    private int userId;
}

