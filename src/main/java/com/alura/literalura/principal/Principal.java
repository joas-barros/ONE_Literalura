package com.alura.literalura.principal;

import com.alura.literalura.model.DadosGutendex;
import com.alura.literalura.model.DadosLivro;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverterDados;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {
    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    ConverterDados converterDados = new ConverterDados();

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

    }

    private void listarAutoresVivosEmUmDeterminadoAno() {
    }

    private void listarAutoresRegistrados() {
    }

    private void listarLivrosRegistrados() {
    }

    private void buscarLivroPeloTitulo() {
        System.out.println("Digite o livro que deseja buscar: ");
        String livro = scanner.nextLine();

        String dados = consumoApi.obterDados(ENDERECO + livro.replace(" ", "%20"));
        DadosGutendex dadosGutendex = converterDados.obterDados(dados, DadosGutendex.class);
        dadosGutendex.results().forEach(System.out::println);
    }
}