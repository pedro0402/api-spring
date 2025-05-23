package com.pedro.jpa.libraryapi.dto;

import com.pedro.jpa.libraryapi.model.Autor;
import com.pedro.jpa.libraryapi.model.GeneroLivro;
import com.pedro.jpa.libraryapi.model.Livro;
import com.pedro.jpa.libraryapi.repository.AutorRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Livro")
public record CadastroLivroDTO(

        @NotBlank(message = "campo obrigatório")
        @ISBN
        String isbn,

        @NotBlank(message = "campo obrigatório")
        String titulo,

        @NotNull(message = "campo obrigatório")
        @Past(message = "não pode ser uma data futura")
        LocalDate dataPublicacao,

        GeneroLivro genero,

        BigDecimal preco,

        @NotNull(message = "campo obrigatório")
        UUID idAutor) {
}
