package com.expensetracker.api.Exception;

import java.time.LocalDateTime;

public record Response(String error, String msg, LocalDateTime timestamp) {
}
