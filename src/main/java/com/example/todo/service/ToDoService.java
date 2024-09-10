package com.example.todo.service;

import com.example.todo.dto.ToDoDto;
import com.example.todo.model.ToDo;
import com.example.todo.model.util.ToDoStatus;
import com.example.todo.repository.ToDoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final HolidayService holidayService;


    public ToDoService(ToDoRepository toDoRepository, HolidayService holidayService) {
        this.toDoRepository = toDoRepository;
        this.holidayService = holidayService;
    }

    public List<ToDo> getAllToDo(ToDoStatus toDoStatus, Integer offset, Integer limit){

        return toDoRepository.findAll(PageRequest.of(offset,limit)).getContent().stream()
                .filter(toDo -> toDo.getStatus() == toDoStatus)
                .toList();
    }

    public ToDo getToDo(long id){
        return toDoRepository.findById(id).orElse(new ToDo());
    }


    public ToDo create(ToDoDto toDoDto) throws Exception {

            if (!toDoDto.getEndDate().isAfter(LocalDate.now())  ||
                    isWeekend(toDoDto.getEndDate()) ||
                    holidayService.getHolidays().containsValue(toDoDto.getEndDate())){
                throw new Exception("Выберите другой день"+openDate());
            }


            ToDo toDo = new ToDo();

            toDo.setTitle(toDoDto.getTitle());
            toDo.setStatus(ToDoStatus.NOT_DONE);
            toDo.setDescription(toDoDto.getDescription());
            toDo.setEndDate(toDoDto.getEndDate());
            toDo.setCreatedDate(LocalDate.now());


            toDoRepository.save(toDo);

            return toDo;
    }

    public ToDo updateToDo(Long id, ToDoDto toDoDto) throws Exception {

        ToDo toDo = toDoRepository.findById(id).orElse(new ToDo());

        if(toDo.hashCode() == 0){
            throw new Exception("Задача с данным id "+id+" не существует");
        }

        toDo.setEndDate(toDoDto.getEndDate());
        toDo.setStatus(toDoDto.getStatus());
        toDo.setTitle(toDoDto.getTitle());
        toDo.setDescription(toDoDto.getDescription());

        toDoRepository.save(toDo);

        return toDo;
    }

    public void deleteToDo(Long id){

        toDoRepository.deleteById(id);
    }


    private LocalDate openDate() throws JsonProcessingException {

        LocalDate currentDate = LocalDate.now();


        while(!isWeekend(currentDate) || !holidayService.getHolidays().containsValue(currentDate)){
            currentDate.plusDays(1);
        }

        return currentDate;
    }

    private boolean isWeekend(final LocalDate ld)
    {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }

}
