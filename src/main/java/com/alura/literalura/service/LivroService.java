package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DadosLivro;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public void salvarLivro(DadosLivro dadosLivro) {

        Optional<Livro> livroOptional = livroRepository.findByTitulo(dadosLivro.title());

        if (livroOptional.isPresent()){
            System.out.println("Livro já cadastrado");
            return;
        }

        Livro livro = new Livro();

        livro.setTitulo(dadosLivro.title());
        livro.setDownloads(dadosLivro.download_count());

        livro.setIdioma(dadosLivro.languages().isEmpty() ? "Unknown" : dadosLivro.languages().get(0));

        List<Autor> autores = dadosLivro.authors().stream()
                .map(dadosAutor -> {
                    Autor novoAutor = new Autor();
                    novoAutor.setNome(dadosAutor.nome());
                    novoAutor.setAnoNascimento(dadosAutor.dataNascimento());
                    novoAutor.setAnoFalecimento(dadosAutor.dataFalecimento());
                    return novoAutor;
                }).collect(Collectors.toList());

        autorRepository.saveAll(autores.stream().filter(autor -> autor.getId() == null).collect(Collectors.toList()));
        livro.setAutores(autores);

        livroRepository.save(livro);
    }

    @Transactional(readOnly = true)
    public List<String> listarLivros() {
        List<Livro> livros = livroRepository.findAll();

        return livros.stream().map(livro -> {
            String autores = livro.getAutores().stream()
                    .map(Autor::getNome)
                    .collect(Collectors.joining(", "));

            return "-------------- Livro ----------------\n" +
                    "Título: " + livro.getTitulo() + "\n" +
                    "Autores: " + autores + "\n" +
                    "Idioma: " + livro.getIdioma() + "\n" +
                    "Número de Downloads: " + livro.getDownloads() + "\n" +
                    "--------------------------------------";
        }).collect(Collectors.toList());
    }

    public Optional<Livro> buscarLivroPeloTitulo(String titulo) {
        return livroRepository.findByTitulo(titulo);
    }

    @Transactional(readOnly = true)
    public List<String> listarLivrosPorIdioma(String idioma) {
        List<Livro> livros = livroRepository.listarLivrosPorIdioma(idioma);

        return livros.stream().map(livro -> {
            String autores = livro.getAutores().stream()
                    .map(Autor::getNome)
                    .collect(Collectors.joining(", "));

            return "-------------- Livro ----------------\n" +
                    "Título: " + livro.getTitulo() + "\n" +
                    "Autores: " + autores + "\n" +
                    "Idioma: " + livro.getIdioma() + "\n" +
                    "Número de Downloads: " + livro.getDownloads() + "\n" +
                    "--------------------------------------";
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> listarTop10LivrosMaisBaixados() {
        List<Livro> livros = livroRepository.findTop10ByOrderByDownloadsDesc();

        // Listar somente o titulo e o número de downloads
        return livros.stream().map(livro -> "Título: " + livro.getTitulo() + " - Downloads: " + livro.getDownloads())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public  List<Livro> buscarTodosLivros() {
        return livroRepository.findAll();
    }
}
