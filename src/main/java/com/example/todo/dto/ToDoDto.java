package com.example.todo.dto;

import com.example.todo.model.util.ToDoStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ToDoDto {

    private String title;

    private String description;

    private ToDoStatus status;

    private LocalDate endDate;
}
