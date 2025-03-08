package com.pedro.jpa.libraryapi.repository;

import com.pedro.jpa.libraryapi.model.Autor;
import com.pedro.jpa.libraryapi.model.GeneroLivro;
import com.pedro.jpa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("45612-21654");
        livro.setPreco(BigDecimal.valueOf(50));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Senhor dos Áneis: A sociedade do anel");
        livro.setDataPublicacao(LocalDate.of(1986, 2, 9));

        Autor autor = autorRepository
                .findById(UUID.fromString("31120726-5fd9-416d-9ab2-d54b5e84b328"))
                .orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarAutorELivroTest() {
        Livro livro = new Livro();
        livro.setIsbn("45612-21654");
        livro.setPreco(BigDecimal.valueOf(58));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Entendendo Algoritmos: Um Guia Ilustrado Para Programadores e Outros Curiosos");
        livro.setDataPublicacao(LocalDate.of(2022, 2, 9));

        Autor autor = new Autor();
        autor.setNome("Aditya Y. Bhargava\n");
        autor.setNacionalidade("Indiano");
        autor.setDataNascimento(LocalDate.of(1994, Month.FEBRUARY, 19));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("78945-54987");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1986, 2, 9));

        Autor autor = new Autor();
        autor.setNome("José");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1994, Month.FEBRUARY, 19));

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivroTest() {
        UUID idLivroParaAtualizar = UUID.fromString("d098d53a-bbba-4ca1-be14-185b25095337");
        Livro livroParaAtualizar = repository.findById(idLivroParaAtualizar).orElse(null);

        UUID idAutor = UUID.fromString("2b8e47ef-051e-431e-9ddc-85648f75b7b5");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(autor);

        repository.save(livroParaAtualizar);
    }

    @Test
    void atualizarNomeAutorDoLivroTest() {
        UUID idLivroParaAtualizar = UUID.fromString("d098d53a-bbba-4ca1-be14-185b25095337");
        Livro livroParaAtualizar = repository.findById(idLivroParaAtualizar).orElse(null);

        UUID idAutor = UUID.fromString("e1c702b9-f999-4852-b13a-03fa17a78a2c");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        autor.setNome("JRR. Tolkien");

        autorRepository.save(autor);

        livroParaAtualizar.setAutor(autor);

        repository.save(livroParaAtualizar);
    }

    @Test
    void atualizarNomeLivroDoLivroTest() {
        UUID idLivroParaAtualizar = UUID.fromString("3389e6e0-303c-41a1-82db-bf6c04e8b4bb");
        Livro livroParaAtualizar = repository.findById(idLivroParaAtualizar).orElse(null);

        livroParaAtualizar.setTitulo("Senhor dos Áneis: As 2 Torres");

        UUID idAutor = UUID.fromString("e1c702b9-f999-4852-b13a-03fa17a78a2c");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(autor);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletarLivroTest() {
        UUID id = UUID.fromString("d098d53a-bbba-4ca1-be14-185b25095337");
        repository.deleteById(id);
    }

    @Test
    @Transactional(readOnly = true)
    void buscarLivroTest() {
        UUID id = UUID.fromString("3389e6e0-303c-41a1-82db-bf6c04e8b4bb");
        Livro livro = repository.findById(id).orElse(null);

        System.out.println("Livro:");
        System.out.println(livro.getTitulo());

        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());

    }

    @Test
    void pesquisaPorTituloTest() {
        List<Livro> livros = repository.findByTitulo("Senhor dos Áneis: As 2 Torres");
        livros.forEach(System.out::println);
    }

    @Test
    void listarLivroIsbnTest() {
        Optional<Livro> livro = repository.findByIsbn("78945-54987");
        livro.ifPresent(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTest() {
        List<Livro> livros = repository.findByTituloAndPreco("Senhor dos Áneis: As 2 Torres", BigDecimal.valueOf(100.00));
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloOuIsbnTest() {
        List<Livro> livros = repository.findByTituloOrIsbn("Senhor dos Áneis: As 2 Torres", "78945-54987");
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisaEntreDatasTest() {
        List<Livro> livros = repository.findByDataPublicacaoBetween(LocalDate.of(2021, Month.FEBRUARY, 10), LocalDate.of(2024, Month.DECEMBER, 24));
        livros.forEach(System.out::println);
    }

    @Test
    void listarTodosOsLivrosTest() {
        List<Livro> livros = repository.listarTodosOsLivros();
        livros.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivrosTest() {
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarNomesLivrosTest() {
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosAutoresBrTest() {
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void findByGeneroQueryParamsTest() {
        var resultado = repository.findByGenero(GeneroLivro.FICCAO, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void findByGeneroPositionalParametersTest() {
        var resultado = repository.findByGeneroPositionalParameters(GeneroLivro.FICCAO, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void deletePorGeneroTest() {
        repository.deleteByGenero(GeneroLivro.FICCAO);
    }

    @Test
    void updateDataPublicacaoPorIdTest(){
        repository.updateDataPublicacao(LocalDate.of(2021, 2, 4),
                UUID.fromString("a429e67f-c38e-4759-a55d-e35a752a7057"));
    }
}