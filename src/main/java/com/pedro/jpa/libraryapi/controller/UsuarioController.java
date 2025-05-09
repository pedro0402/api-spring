package com.pedro.jpa.libraryapi.controller;

import com.pedro.jpa.libraryapi.dto.UsuarioDTO;
import com.pedro.jpa.libraryapi.mappers.UsuarioMapper;
import com.pedro.jpa.libraryapi.model.Usuario;
import com.pedro.jpa.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Salvar", description = "Salvar um usuário")
    public void salvar(@RequestBody @Valid UsuarioDTO dto){
        Usuario entity = usuarioMapper.toEntity(dto);
        usuarioService.salvar(entity);
    }
}
