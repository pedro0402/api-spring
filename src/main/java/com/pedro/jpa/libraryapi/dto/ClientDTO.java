package com.pedro.jpa.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(name = "Client")
public record ClientDTO(
        UUID id,

        @NotNull(message = "Campo obrigatório")
        @Size(min = 3, max = 150, message = "Campo fora do tamanho padrão")
        String clientId,

        @NotNull(message = "Campo obrigatório")
        @Size(min = 3, max = 400, message = "Campo fora do tamanho padrão")
        String clientSecret,

        @NotNull(message = "Campo obrigatório")
        @Size(min = 3, max = 400, message = "Campo fora do tamanho padrão")
        String redirectURI,

        String scope
) {
}
