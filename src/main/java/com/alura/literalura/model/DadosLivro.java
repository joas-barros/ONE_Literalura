package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<DadosAutor> authors,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") Integer download_count
) {


    @Override
    public String toString() {

        String autores = authors.stream()
                .map(DadosAutor::nome)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Unknown");

        return "-------------- Livro ----------------\n" +
                "Titulo: " + title + "\n" +
                "Autores: " + autores + "\n" +
                "Idiomas: " + languages + "\n" +
                "Downloads: " + download_count + "\n" +
                "------------------------------------";
    }
}
