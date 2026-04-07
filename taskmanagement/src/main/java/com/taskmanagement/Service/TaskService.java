package com.taskmanagement.Service;

import com.taskmanagement.Dto.TaskGetResponsedto;
import com.taskmanagement.Dto.TaskRequestdto;
import com.taskmanagement.Dto.TaskResponsedto;
import com.taskmanagement.Exception.ResourceNotFoundException;
import com.taskmanagement.Mapper.TaskMapper;
import com.taskmanagement.Model.Task;
import com.taskmanagement.Repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class TaskService {
private final TaskRepository taskRepository;

    public void addNewTask(TaskRequestdto taskRequestdto) {
        Task task=new Task();
        task.setTitle(taskRequestdto.title());
        task.setDescription(taskRequestdto.description());
        task.setDueDate(taskRequestdto.dueDate());
        task.setPriority(taskRequestdto.priority());
        task.setStatus(taskRequestdto.status());
//        save the task in database
       taskRepository.save(task);
    }

    public TaskGetResponsedto getAllTask(int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
         Page<Task> taskList=taskRepository.findAll(pageable);
         List<TaskResponsedto>taskResponsedtoList=taskList.toList().stream().map(TaskMapper::MapToDto).toList();
         Long totalElement=taskList.getTotalElements();
         int totalPages=taskList.getTotalPages();
         return new TaskGetResponsedto(taskResponsedtoList,totalElement,totalPages);
    }

    public TaskResponsedto getTaskById(Long id) {

        Task task=taskRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The given id is invalid"));
        return TaskMapper.MapToDto(task);
    }

    public void updateTaskById(Long id, TaskRequestdto taskRequestdto) {
//        step-1 finding out the task exist with particular this id or not
        Task task=taskRepository.findById(id).orElseThrow(()->
                new RuntimeException("The given id is invalid"));
//        step-2 updating the details of task
        task.setTitle(taskRequestdto.title());
        task.setDescription(taskRequestdto.description());
        task.setDueDate(taskRequestdto.dueDate());
        task.setPriority(taskRequestdto.priority());
        task.setStatus(taskRequestdto.status());
//        step-3 save things in db
        taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
//        step-1 finding the task exist with this id or not
        Task task=taskRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("The given id is invalid"));
//        step-2 deleting the task
        taskRepository.deleteById(id);
    }
}
