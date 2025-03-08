package com.pedro.jpa.libraryapi.repository.specs;

import com.pedro.jpa.libraryapi.model.GeneroLivro;
import com.pedro.jpa.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return ((root, query, cb) ->
                cb.equal(root.get("isbn"), isbn));
    }

    public static Specification<Livro> tituloLike(String titulo) {
        //utilizando o .upper, eu coloco meu titulo para maiusculo
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro generoLivro) {
        return (root, query, cb) ->
                cb.equal(root.get("genero"), generoLivro);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
        return (root, query, cb) -> cb.equal(cb.function("to_char",
                        String.class, root.get("dataPublicacao"),
                        cb.literal("YYYY")),
                anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root, query, cb) -> {
//            return cb.like(cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%");
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }

}
