package com.pedro.jpa.libraryapi.repository;

import com.pedro.jpa.libraryapi.model.Autor;
import com.pedro.jpa.libraryapi.model.GeneroLivro;
import com.pedro.jpa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;
import java.time.Month;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("José");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1994, Month.FEBRUARY, 19));

        var autorSalvo = autorRepository.save(autor);

        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest() {
        UUID id = UUID.fromString("0bf71cc4-d50d-4e5f-bf16-13aaf0be83a4");

        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if (possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setNacionalidade("Mexicano");

            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> autorList = autorRepository.findAll();
        for (Autor autor : autorList) {
            System.out.println(autor);
        }
    }

    @Test
    public void count() {
        System.out.println("contagem de autores: " + autorRepository.count());
    }

    @Test
    public void deleteTest() {
        UUID id = UUID.fromString("d0109cf0-4d33-4bbd-bf0a-97de24fff2ab");
        autorRepository.deleteById(id);
    }

    @Test
    void salvarAutorComLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("Scoot H. Young");
        autor.setNacionalidade("Norte americano");
        autor.setDataNascimento(LocalDate.of(1989, Month.AUGUST, 29));

        Livro livro = new Livro();
        livro.setIsbn("12345-54321");
        livro.setPreco(BigDecimal.valueOf(38.32));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Ultra-aprendizado");
        livro.setDataPublicacao(LocalDate.of(2022, 5, 19));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("45612-21654");
        livro2.setPreco(BigDecimal.valueOf(50.46));
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setTitulo("Get Better at Anything");
        livro2.setDataPublicacao(LocalDate.of(2024, 3, 20));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);
        //livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void listarLivrosAutorTest() {
        UUID id = UUID.fromString("20df5d04-b66a-4661-bd65-ee0a7c6e5414");

        Autor autor = autorRepository.findById(id).get();

        List<Livro> livroList = livroRepository.findByAutor(autor);
        autor.setLivros(livroList);

        for (Livro livro : autor.getLivros()) {
            System.out.println(livro);
        }
    }
}
