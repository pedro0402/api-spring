package com.pedro.jpa.libraryapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "Campo obrigatório")
        String login,
        @NotBlank(message = "Campo obrigatório")
        String senha,
        @Email
        @NotBlank(message = "Campo obrigatório")
        String email,
        List<String> roles) {
}
