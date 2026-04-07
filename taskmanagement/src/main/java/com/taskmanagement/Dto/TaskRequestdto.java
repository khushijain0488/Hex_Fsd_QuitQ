package com.taskmanagement.Dto;

import com.taskmanagement.enums.Priority;
import com.taskmanagement.enums.Status;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;

public record TaskRequestdto(
        @NotNull

         String title,
         String description,
         LocalDate dueDate,
         Priority priority,
         Status status


) {
}
