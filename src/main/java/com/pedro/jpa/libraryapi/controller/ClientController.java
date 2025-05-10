package com.pedro.jpa.libraryapi.controller;

import com.pedro.jpa.libraryapi.dto.ClientDTO;
import com.pedro.jpa.libraryapi.mappers.ClientMapper;
import com.pedro.jpa.libraryapi.model.Client;
import com.pedro.jpa.libraryapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
@Tag(name = "Clients")
public class ClientController implements GenericController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Salvar um client")
    @ApiResponse(responseCode = "201", description = "Client cadastrado com sucesso")
    public ResponseEntity<Void> salvar(@RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        clientService.save(client);
        URI uri = gerarHeaderLocation(client.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> updateClient(@PathVariable String id, @RequestBody @Valid ClientDTO clientDTO){
        UUID uuid = UUID.fromString(id);
        Optional<Client> clientOptional = clientService.findById(uuid);

        if (clientOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Client client = clientOptional.get();
        client.setClientId(clientDTO.clientId());
        client.setClientSecret(clientDTO.clientSecret());
        client.setRedirectURI(clientDTO.redirectURI());

        clientService.update(client);

        return ResponseEntity.noContent().build();
    }

}
