package com.pedro.jpa.libraryapi.validator;

import com.pedro.jpa.libraryapi.exceptions.CampoInvalidoException;
import com.pedro.jpa.libraryapi.exceptions.RegistroDuplicadoException;
import com.pedro.jpa.libraryapi.model.Livro;
import com.pedro.jpa.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository livroRepository;

    public void validar(Livro livro){
        if (existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado.");
        }
        if (isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preço", "Para livros com ano >= 2020, o preço é obrigatório");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro> livroOptional = livroRepository.findByIsbn(livro.getIsbn());

        if (livro.getId() == null){
            return livroOptional.isPresent();
        }

        return livroOptional
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }
}
