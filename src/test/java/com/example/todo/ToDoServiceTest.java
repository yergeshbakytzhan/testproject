package com.example.todo;


import com.example.todo.model.ToDo;
import com.example.todo.model.util.ToDoStatus;
import com.example.todo.repository.ToDoRepository;
import com.example.todo.service.ToDoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {

    @InjectMocks
    private ToDoService toDoService;

    @Mock
    private ToDoRepository toDoRepository;


    @Test
    @Order(1)
    public void testToDoGetById(){

        long toDoId = 1;

        ToDo toDoMock = new ToDo(toDoId, "title", "description", LocalDate.now(), ToDoStatus.NOT_DONE,LocalDate.now().plusDays(1));


        Mockito.when(toDoRepository.findById(toDoId)).thenReturn(Optional.of(toDoMock));

        ToDo result = toDoService.getToDo(toDoId);

        assertEquals(toDoId, result.getId());

    }

    @Test
    @Order(2)
    public void testGetAllToDo(){

        List<ToDo> employees = toDoRepository.findAll();


        Assertions.assertThat(employees.size()).isGreaterThan(0);

    }


    @Test
    @Order(3)
    @Rollback(value = false)
    public void testCreateToDo(){

        ToDo toDoMock = new ToDo(1L,"title", "description", LocalDate.now(), ToDoStatus.NOT_DONE,LocalDate.now().plusDays(1));

        toDoRepository.save(toDoMock);


        Assertions.assertThat(toDoMock.getId()).isGreaterThan(0);

    }


    @Test
    @Order(4)
    @Rollback(value = false)
    public void testUpdateToDo(){

        ToDo toDoMock = toDoRepository.findById(1L).orElse(new ToDo());
        toDoMock.setDescription("description");
        ToDo toDoMockUpdated =  toDoRepository.save(toDoMock);

        System.out.println(toDoMockUpdated);
        Assertions.assertThat(toDoMockUpdated.getDescription()).isEqualTo("description");

    }


    @Test
    @Order(5)
    @Rollback(value = false)
    public void testDeleteToDo(){

        toDoRepository.deleteById(1L);
        Optional<ToDo> toDoOptional = toDoRepository.findById(1L);

        Assertions.assertThat(toDoOptional).isEmpty();
    }


}
