package com.pedro.jpa.libraryapi.service;

import com.pedro.jpa.libraryapi.dto.AutorDTO;
import com.pedro.jpa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.pedro.jpa.libraryapi.model.Autor;
import com.pedro.jpa.libraryapi.model.Usuario;
import com.pedro.jpa.libraryapi.repository.AutorRepository;
import com.pedro.jpa.libraryapi.repository.LivroRepository;
import com.pedro.jpa.libraryapi.security.SecurityService;
import com.pedro.jpa.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final AutorValidator validator;
    private final SecurityService securityService;


    public Autor salvar(Autor autor) {
        validator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é preciso que o autor já esteja cadastrado na base.");
        }
        validator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID uuid) {
        return autorRepository.findById(uuid);
    }

    public void deletar(Autor autor) {
        if (possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException("Não é possível excluir um autor que possui livro cadastrados!");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        } else if (nome != null) {
            return autorRepository.findByNome(nome);
        } else if (nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        } else {
            return autorRepository.findAll();
        }
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> example = Example.of(autor, matcher);
        return autorRepository.findAll(example);
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }

}
