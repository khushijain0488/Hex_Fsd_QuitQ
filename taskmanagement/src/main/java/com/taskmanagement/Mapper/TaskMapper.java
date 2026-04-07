package com.taskmanagement.Mapper;

import com.taskmanagement.Dto.TaskResponsedto;
import com.taskmanagement.Model.Task;

public class TaskMapper {
    public static TaskResponsedto MapToDto(Task task){
        return new TaskResponsedto(task.getTitle(),task.getDescription(),task.getDueDate(),task.getPriority(),task.getStatus());
    }
}
