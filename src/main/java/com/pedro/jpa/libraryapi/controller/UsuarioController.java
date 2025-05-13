package com.pedro.jpa.libraryapi.controller;

import com.pedro.jpa.libraryapi.dto.UserResponseDTO;
import com.pedro.jpa.libraryapi.dto.UsuarioDTO;
import com.pedro.jpa.libraryapi.mappers.UsuarioMapper;
import com.pedro.jpa.libraryapi.model.Usuario;
import com.pedro.jpa.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários")
public class UsuarioController implements GenericController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Salvar um usuário")
    public ResponseEntity<Void> salvar(@RequestBody @Valid UsuarioDTO dto) {
        Usuario entity = usuarioMapper.toEntity(dto);
        usuarioService.salvar(entity);
        return ResponseEntity.created(gerarHeaderLocation(entity.getId())).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> updateUser(@PathVariable String id, @RequestBody @Valid UserRequestDTO userRequestDTO) {
        UUID userId = UUID.fromString(id);
        Optional<Usuario> usuarioOptional = usuarioService.findById(userId);

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioOptional.get();
        usuario.setLogin(userRequestDTO.login());
        usuario.setSenha(userRequestDTO.senha());
        usuario.setEmail(userRequestDTO.email());

        usuarioService.update(usuario);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Usuario> usuarioOptional = usuarioService.findById(uuid);

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioService.delete(usuarioOptional.get());
        return ResponseEntity.noContent().build();
    }
}
