package com.example.todo.controller;

import com.example.todo.dto.ToDoDto;
import com.example.todo.model.ToDo;
import com.example.todo.model.util.ToDoStatus;
import com.example.todo.service.ToDoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("todo")
public class ToDoController {

    private final ToDoService toDoService;


    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }


    @GetMapping()
    public List<ToDo> getAllToDo(
            @RequestParam(value = "toDoStatus", defaultValue = "0") ToDoStatus toDoStatus,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "0") Integer limit) {

        return toDoService.getAllToDo(toDoStatus, offset, limit);
    }

    @GetMapping("/{id}")
    public ToDo getToDo(@PathVariable long id){
        return toDoService.getToDo(id);
    }

    @PostMapping()
    public ToDo create(@RequestBody ToDoDto toDoDto) throws Exception {

        return toDoService.create(toDoDto);
    }

    @PutMapping("/{id}")
    public ToDo updateToDo(@PathVariable long id, @RequestBody ToDoDto toDoDto) throws Exception {
        return toDoService.updateToDo(id, toDoDto);
    }


    @DeleteMapping("/{id}")
    public void deleteToDo(@PathVariable long id){
        toDoService.deleteToDo(id);
    }


}
