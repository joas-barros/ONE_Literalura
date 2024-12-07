package com.alura.literalura.principal;

import com.alura.literalura.model.DadosGutendex;
import com.alura.literalura.model.DadosLivro;
import com.alura.literalura.model.Livro;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverterDados;
import com.alura.literalura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {
    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    ConverterDados converterDados = new ConverterDados();

    @Autowired
    private LivroService livroService;

    @Autowired
    private AutorService autorService;

    private final String ENDERECO = "https://gutendex.com/books/?search=";

    public void exibirMenu() {

        int opcao = -1;
        while (opcao != 0) {
            String menu = """
                    ----------------------------
                    Escolha o número de sua opção:
                    1- buscar livro pelo título
                    2- listar livros registrados
                    3- listar autores registrados
                    4- listar autores vivos em um determinado ano
                    5- listar livros em um determinado idioma
                    0- sair                                 
                    ----------------------------
                    """;

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1 -> buscarLivroPeloTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosEmUmDeterminadoAno();
                case 5 -> listarLivrosEmUmDeterminadoIdioma();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida");
            }
        }

    }

    private void listarLivrosEmUmDeterminadoIdioma() {

        System.out.println("Escolha o idioma que deseja buscar: ");
        System.out.println("""
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                de - alemão
                """);

        String idioma = scanner.nextLine();

        List<String> livrosPorIdioma = livroService.listarLivrosPorIdioma(idioma);

        if (livrosPorIdioma.isEmpty()) {
            System.out.println("Nenhum livro encontrado");
        } else {
            livrosPorIdioma.forEach(System.out::println);
        }

    }

    private void listarAutoresVivosEmUmDeterminadoAno() {
        System.out.println("Digite o ano que deseja buscar: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        List<String> autoresVivos = autorService.listarAutoresVivosEmUmDeterminadoAno(ano);

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado");
        } else {
            autoresVivos.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<String> autoresRegistrados = autorService.listarAutores();

        if (autoresRegistrados.isEmpty()) {
            System.out.println("Nenhum autor cadastrado");
        } else {
            autoresRegistrados.forEach(System.out::println);
        }
    }

    private void listarLivrosRegistrados() {
        List<String> livrosRegistrados = livroService.listarLivros();

        if (livrosRegistrados.isEmpty()) {
            System.out.println("Nenhum livro cadastrado");
        } else {
            livrosRegistrados.forEach(System.out::println);
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.println("Digite o livro que deseja buscar: ");
        String livro = scanner.nextLine();

        String dados = consumoApi.obterDados(ENDERECO + livro.replace(" ", "%20"));
        DadosGutendex dadosGutendex = converterDados.obterDados(dados, DadosGutendex.class);

        if (dadosGutendex.results() != null && !dadosGutendex.results().isEmpty()) {
            for (DadosLivro dadosLivro : dadosGutendex.results()) {
                System.out.println(dadosLivro);

                if (livroService.buscarLivroPeloTitulo(dadosLivro.title()).isPresent()) {
                    System.out.println("Livro já cadastrado");
                } else {
                    livroService.salvarLivro(dadosLivro);
                    System.out.println("Livro cadastrado com sucesso");
                }
            }
        } else {
            System.out.println("Livro não encontrado");
        }
    }
}