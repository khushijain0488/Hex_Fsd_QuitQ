package com.taskmanagement.Dto;

import com.taskmanagement.Model.Task;

import java.util.List;

public record TaskGetResponsedto(
        List<TaskResponsedto>data,
        Long TotalElement,
        int Totalpages
) {
}
