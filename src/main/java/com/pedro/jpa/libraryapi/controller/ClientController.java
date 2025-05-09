package com.pedro.jpa.libraryapi.controller;

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

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Salvar um client")
    @ApiResponse(responseCode = "201", description = "Client cadastrado com sucesso")
    public ResponseEntity<Void> salvar(@RequestBody Client client) {
        //não é o ideal cadastrar logo a entidade. o ideal seria criar um dto e dps mapea-lo.
        clientService.salvar(client);
        URI uri = gerarHeaderLocation(client.getId());
        return ResponseEntity.created(uri).build();
    }
}
