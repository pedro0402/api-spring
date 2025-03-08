package com.pedro.jpa.libraryapi.controller;

import com.pedro.jpa.libraryapi.dto.UsuarioDTO;
import com.pedro.jpa.libraryapi.mappers.UsuarioMapper;
import com.pedro.jpa.libraryapi.model.Usuario;
import com.pedro.jpa.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto){
        Usuario entity = usuarioMapper.toEntity(dto);
        usuarioService.salvar(entity);
    }
}
