package com.pedro.jpa.libraryapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserResponseDTO(
        @NotBlank(message = "Campo obrigatório")
        String login,
        @Email
        @NotBlank(message = "Campo obrigatório")
        String email,
        @NotBlank(message = "Campo obrigatório")
        List<String> roles) {
}
