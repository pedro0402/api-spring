package com.pedro.jpa.libraryapi.mappers;

import com.pedro.jpa.libraryapi.dto.ClientDTO;
import com.pedro.jpa.libraryapi.dto.ClientResponseDTO;
import com.pedro.jpa.libraryapi.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDTO clientDTO);

    ClientDTO toClientDTO(Client client);

    ClientResponseDTO toClientResponseDTO(Client client);
}
