package com.pedro.jpa.libraryapi.service;

import com.pedro.jpa.libraryapi.model.Autor;
import com.pedro.jpa.libraryapi.model.GeneroLivro;
import com.pedro.jpa.libraryapi.model.Livro;
import com.pedro.jpa.libraryapi.repository.AutorRepository;
import com.pedro.jpa.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;


    @Transactional
    public void atualizacaoSemAtualizar() {
        Livro livro = livroRepository.findById(UUID.fromString("f2e85789-d802-4fac-8fab-fcde0b57bf1d")).orElse(null);
        livro.setDataPublicacao(LocalDate.of(2022, 5, 29));
        livroRepository.save(livro);
    }

    @Transactional
    public void executar() {
        Autor autor = new Autor();
        autor.setNome("John Green");
        autor.setNacionalidade("Espanhol");
        autor.setDataNascimento(LocalDate.of(1994, Month.FEBRUARY, 19));

        autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setIsbn("45612-21654");
        livro.setPreco(BigDecimal.valueOf(93));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Operação Cavalo de Troia");
        livro.setDataPublicacao(LocalDate.of(2004, 2, 9));
        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals("John Green")) {
            throw new RuntimeException("RollBack!");
        }
    }
}
