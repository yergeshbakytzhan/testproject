package com.example.todo.scheduler;

import com.example.todo.model.ToDo;
import com.example.todo.model.util.ToDoStatus;
import com.example.todo.repository.ToDoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class StatusCheckScheduler {


    private final ToDoRepository toDoRepository;

    public StatusCheckScheduler(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    //everyday at 12:10 am
    @Scheduled(cron = "10 0 * * * *")
    public void statusCheck(){
        List<ToDo> toDoList = getAllNotDone();

        for(ToDo toDo: toDoList){
            if(toDo.getEndDate().isBefore(LocalDate.now())){
                toDo.setStatus(ToDoStatus.EXPIRED);
            }

        }
    }


    private List<ToDo> getAllNotDone(){

        return toDoRepository.findAll().stream()
                .filter(toDo -> toDo.getStatus() == ToDoStatus.NOT_DONE)
                .toList();

    }

}
