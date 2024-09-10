package com.example.todo.controller;


import com.example.todo.service.HolidayService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
@RequestMapping("holiday")
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }


    @GetMapping()
    public HashMap<Integer, LocalDate> getHolidays() throws JsonProcessingException {

        return holidayService.getHolidays();
    }

}
