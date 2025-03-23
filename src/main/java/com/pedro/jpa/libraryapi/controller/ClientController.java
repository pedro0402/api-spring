package com.pedro.jpa.libraryapi.controller;

import com.pedro.jpa.libraryapi.model.Client;
import com.pedro.jpa.libraryapi.service.ClientService;
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
public class ClientController implements GenericController {

    private final ClientService clientService;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody Client client) {
        //não é o ideal cadastrar logo a entidade. o ideal seria criar um dto e dps mapea-lo.
        clientService.salvar(client);
        URI uri = gerarHeaderLocation(client.getId());
        return ResponseEntity.created(uri).build();
    }
}
