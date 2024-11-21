package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {


    public static void main(String[] args) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString("{ \"id\" : 1 }");
            System.out.println(jsonData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}