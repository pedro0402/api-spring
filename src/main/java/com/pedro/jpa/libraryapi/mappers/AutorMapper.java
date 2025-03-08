package com.pedro.jpa.libraryapi.mappers;

import com.pedro.jpa.libraryapi.dto.AutorDTO;
import com.pedro.jpa.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome")
    Autor toEntity (AutorDTO dto);

    AutorDTO toDTO (Autor autor);

}
