package com.pedro.jpa.libraryapi.mappers;

import com.pedro.jpa.libraryapi.dto.CadastroLivroDTO;
import com.pedro.jpa.libraryapi.dto.ResultadoPesquisaLivroDTO;
import com.pedro.jpa.libraryapi.model.Livro;
import com.pedro.jpa.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);

}
