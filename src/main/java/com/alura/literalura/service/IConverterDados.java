package com.alura.literalura.service;

public interface IConverterDados {
    <T> T obterDados(String dados, Class<T> classe);
}
