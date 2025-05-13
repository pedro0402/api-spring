package com.pedro.jpa.libraryapi.controller;

import com.pedro.jpa.libraryapi.dto.UserRequestDTO;
import com.pedro.jpa.libraryapi.dto.UserResponseDTO;
import com.pedro.jpa.libraryapi.dto.UsuarioDTO;
import com.pedro.jpa.libraryapi.mappers.UsuarioMapper;
import com.pedro.jpa.libraryapi.model.Usuario;
import com.pedro.jpa.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @Operation(summary = "Salvar", description = "Salvando um novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário salvo com sucesso")
    public ResponseEntity<Void> salvar(@RequestBody @Valid UsuarioDTO dto) {
        Usuario entity = usuarioMapper.toEntity(dto);
        usuarioService.salvar(entity);
        return ResponseEntity.created(gerarHeaderLocation(entity.getId())).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Atualizar", description = "Atualiza um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
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
    @Operation(summary = "Deletar", description = "Deleta um usário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Usuario> usuarioOptional = usuarioService.findById(uuid);

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioService.delete(usuarioOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'OPERADOR')")
    @Operation(summary = "Buscar", description = "Busca os usuários")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca feita com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sem usuários para fazer a busca")
    })
    public ResponseEntity<List<UserResponseDTO>> findUsers() {
        List<Usuario> usuarioList = usuarioService.findAllUsers();

        if (usuarioList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                usuarioList.stream()
                        .map(usuarioMapper::toUserResponseDto)
                        .toList()
        );
    }
}
