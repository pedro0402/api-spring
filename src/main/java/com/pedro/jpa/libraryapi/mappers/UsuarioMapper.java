package com.pedro.jpa.libraryapi.mappers;

import com.pedro.jpa.libraryapi.dto.UserResponseDTO;
import com.pedro.jpa.libraryapi.dto.UsuarioDTO;
import com.pedro.jpa.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO usuarioDTO);

    UserResponseDTO toUserResponseDto(Usuario usuario);
}
