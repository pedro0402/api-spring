package com.pedro.jpa.libraryapi.dto;

import java.util.List;

public record UsuarioDTO(String login, String senha, List<String> roles) {
}
