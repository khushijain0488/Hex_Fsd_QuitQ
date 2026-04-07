package com.taskmanagement.Dto;

import com.taskmanagement.enums.Priority;
import com.taskmanagement.enums.Status;

import java.time.LocalDate;

public record TaskResponsedto(
        String title,
        String description,
        LocalDate dueDate,
        Priority priority,
        Status status
) {
}
