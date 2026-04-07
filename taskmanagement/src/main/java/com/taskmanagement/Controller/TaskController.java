package com.taskmanagement.Controller;

import com.taskmanagement.Dto.TaskGetResponsedto;
import com.taskmanagement.Dto.TaskRequestdto;
import com.taskmanagement.Dto.TaskResponsedto;
import com.taskmanagement.Model.Task;
import com.taskmanagement.Service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
//    adding new task
    @PostMapping("/add")
    public ResponseEntity<?>addNewTask(@Valid @RequestBody TaskRequestdto taskRequestdto){
        taskService.addNewTask(taskRequestdto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
//    getting all task store db
    @GetMapping("/get-all")
    public ResponseEntity<TaskGetResponsedto>getAllTask(@RequestParam(value = "page",required = false,defaultValue = "0")int page,
                                                        @RequestParam(value = "size",required = false,defaultValue = "5")int size){
        return ResponseEntity.ok(taskService.getAllTask(page,size));
    }
//    getting task by id
    @GetMapping("/get/{id}")
    public ResponseEntity<TaskResponsedto>getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }
//update and existing task
    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateTaskById(@PathVariable Long id,@RequestBody TaskRequestdto taskRequestdto){
        taskService.updateTaskById(id,taskRequestdto);
        return ResponseEntity.ok("Task updated successfully");
    }
//    deleting the task by its own id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteTaskById(@PathVariable Long id){
        taskService.deleteTaskById(id);
        return ResponseEntity.ok("Task deleted successfully");
    }

}
