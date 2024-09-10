package com.example.todo.model;

import com.example.todo.model.util.ToDoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TODO")
public class ToDo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private LocalDate createdDate;

    private ToDoStatus status;

    private LocalDate endDate;

}
