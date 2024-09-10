package com.example.todo.service;

import com.example.todo.model.Holiday;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Year;
import java.util.HashMap;
import java.util.List;

@Service
public class HolidayService {

    private  final ObjectMapper mapper;

    public HolidayService(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    public HashMap<Integer, LocalDate> getHolidays() throws JsonProcessingException {

        HashMap<Integer,LocalDate> cache = new HashMap<>();

        int year = Year.now().getValue();

        String locale = LocaleContextHolder.getLocale().getCountry();

        String uri="https://date.nager.at/api/v3/PublicHolidays/"+year+"/"+locale;
        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject(uri, String.class);

        List<Holiday> holidays = mapper.readValue(result, new TypeReference<>() {});

        for(int i=0; i<holidays.size();i++){
            cache.put(i, holidays.get(i).getDate());
        }

        return cache;
    }
}
