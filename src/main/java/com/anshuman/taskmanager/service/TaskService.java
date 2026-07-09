package com.anshuman.taskmanager.service;

import com.anshuman.taskmanager.dto.TaskRequest;
import com.anshuman.taskmanager.dto.TaskResponse;
import com.anshuman.taskmanager.entity.Task;
import com.anshuman.taskmanager.entity.User;
import com.anshuman.taskmanager.exception.TaskNotFoundException;
import com.anshuman.taskmanager.exception.UnauthorizedException;
import com.anshuman.taskmanager.repository.TaskRepository;
import com.anshuman.taskmanager.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setStatus(false);
        task.setCreatedAt(LocalDateTime.now());
        User currentUser = getCurrentUser();
       task.setUser(currentUser);
       taskRepository.save(task);

        return convertToResponse(task);
    }
    private TaskResponse convertToResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setDeadline(task.getDeadline());
        taskResponse.setStatus(false);
        taskResponse.setCreatedAt(task.getCreatedAt());
        return taskResponse;
    }

    public List<TaskResponse> getTasks() {

        User currentUser = getCurrentUser();
        List<Task> tasks = taskRepository.findByUser(currentUser);
        List<TaskResponse> responses = new ArrayList<>();
        for(Task task : tasks){
            responses.add(convertToResponse(task));
        }
        return responses;
    }
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return currentUser;
    }
    private Task getTaskByIdOrThrow(Long id){
         return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("task not found"));
    }
    public TaskResponse getTaskById(Long id) {

        Task task = getTaskByIdOrThrow(id);

        User currentUser = getCurrentUser();

        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not allowed to access this task");
        }

        return convertToResponse(task);
    }
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = getTaskByIdOrThrow(id);
        User currentUser = getCurrentUser();
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not allowed to access this task");
        }
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        taskRepository.save(task);
        return convertToResponse(task);
    }
    public String deleteTask(Long id) {
        Task task = getTaskByIdOrThrow(id);
        User currentUser = getCurrentUser();
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You are not allowed to access this task");
        }
        taskRepository.delete(task);
        return "Successfully deleted this task";
    }

}
