package com.alura.literalura.model;

import java.util.List;

public record DadosGutendex(
        Integer count,
        String next,
        String previous,
        List<DadosLivro> results
) {
}
