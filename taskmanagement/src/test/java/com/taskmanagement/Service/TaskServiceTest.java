package com.taskmanagement.Service;

import com.taskmanagement.Dto.TaskGetResponsedto;
import com.taskmanagement.Dto.TaskResponsedto;
import com.taskmanagement.Exception.ResourceNotFoundException;
import com.taskmanagement.Model.Task;
import com.taskmanagement.Repository.TaskRepository;
import com.taskmanagement.enums.Priority;
import com.taskmanagement.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void getTaskByIdWhenExists() {
        Assertions.assertNotNull(taskService);

        // Preparing mock task
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setDueDate(LocalDate.of(2025, 12, 31));
        task.setPriority(Priority.HIGH);
        task.setStatus(Status.PENDING);

        // Mocking repository
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Preparing expected DTO
        TaskResponsedto dto = new TaskResponsedto(
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        );

        // Wrong DTO
        TaskResponsedto dto1 = new TaskResponsedto(
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                Priority.LOW,       // wrong priority
                task.getStatus()
        );

        Assertions.assertEquals(dto, taskService.getTaskById(1L));
        Assertions.assertNotEquals(dto1, taskService.getTaskById(1L));

        Mockito.verify(taskRepository, times(2)).findById(1L);
    }


    @Test
    public void getTaskByIdWhenNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getTaskById(99L);
        });

        Assertions.assertEquals("The given id is invalid", e.getMessage());
    }

    @Test
    public void getAllTaskTest() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task One");
        task1.setDescription("Desc One");
        task1.setDueDate(LocalDate.of(2025, 10, 10));
        task1.setPriority(Priority.LOW);
        task1.setStatus(Status.PENDING);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task Two");
        task2.setDescription("Desc Two");
        task2.setDueDate(LocalDate.of(2025, 11, 11));
        task2.setPriority(Priority.HIGH);
        task2.setStatus(Status.COMPLETED);

        List<Task> list = List.of(task1, task2);

        // Page with size 1
        Page<Task> pageTask1 = new PageImpl<>(list.subList(0, 1));
        Pageable pageable1 = PageRequest.of(0, 1);

        when(taskRepository.findAll(pageable1)).thenReturn(pageTask1);

        Assertions.assertEquals(1, taskService.getAllTask(0, 1).data().size());
    }
}