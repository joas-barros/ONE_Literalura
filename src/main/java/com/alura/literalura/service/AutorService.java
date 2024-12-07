package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Transactional(readOnly = true)
    public List<String> listarAutores() {
        List<Autor> autores = autorRepository.findAll();

        return autores.stream()
                .map( autor -> {
                    String livros = autor.getLivros().stream()
                            .map(Livro::getTitulo)
                            .collect(Collectors.joining(", "));

                    return "Autor: " + autor.getNome() + "\n" +
                            "Ano de nascimento: " + autor.getAnoNascimento() + "\n" +
                            "Ano de falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "N/A") + "\n" +
                            "Livros: [" + livros + "]\n";
                }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> listarAutoresVivosEmUmDeterminadoAno(int ano) {
        List<Autor> autores = autorRepository.findAll();

        return autores.stream()
                .filter(autor -> autor.getAnoFalecimento() == null || ( autor.getAnoFalecimento() > ano && autor.getAnoNascimento() <= ano))
                .map( autor -> {
                    String livros = autor.getLivros().stream()
                            .map(Livro::getTitulo)
                            .collect(Collectors.joining(", "));

                    return "Autor: " + autor.getNome() + "\n" +
                            "Ano de nascimento: " + autor.getAnoNascimento() + "\n" +
                            "Ano de falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "N/A") + "\n" +
                            "Livros: [" + livros + "]\n";
                }).collect(Collectors.toList());
    }
}
