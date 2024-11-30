package com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterDados implements IConverterDados {

    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public <T> T obterDados(String dados, Class<T> classe) {
        try{
            return mapper.readValue(dados, classe);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
