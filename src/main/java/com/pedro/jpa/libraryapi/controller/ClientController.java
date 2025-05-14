package com.pedro.jpa.libraryapi.controller;

import com.pedro.jpa.libraryapi.dto.ClientDTO;
import com.pedro.jpa.libraryapi.dto.ClientResponseDTO;
import com.pedro.jpa.libraryapi.mappers.ClientMapper;
import com.pedro.jpa.libraryapi.model.Client;
import com.pedro.jpa.libraryapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
@Tag(name = "Clients")
@Slf4j
public class ClientController implements GenericController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'OPERADOR')")
    @Operation(summary = "Buscar", description = "Busca os clientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca feita com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sem clients para fazer a busca")
    })
    public ResponseEntity<List<ClientResponseDTO>> findClients() {
        List<Client> clients = clientService.findAllClients();

        if (clients.isEmpty()) {
            log.warn("Nenhum cliente encontrado na base de dados");
            return ResponseEntity.notFound().build();
        }

        log.info("Total de clientes encontrados: {}", clients.size());
        return ResponseEntity.ok(clients.stream().map(clientMapper::toClientResponseDTO).toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Salvar um client")
    @ApiResponse(responseCode = "201", description = "Client cadastrado com sucesso")
    public ResponseEntity<Void> save(@RequestBody ClientDTO clientDTO) {
        log.info("Registrando novo Client: {} com scope {}", clientDTO.clientId(), clientDTO.scope());
        Client client = clientMapper.toEntity(clientDTO);
        clientService.save(client);
        URI uri = gerarHeaderLocation(client.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Atualizar", description = "Atualizar um client")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Client não encontrado"),
            @ApiResponse(responseCode = "204", description = "Client atualizado com sucesso")
    })
    public ResponseEntity<Void> updateClient(@PathVariable String id, @RequestBody @Valid ClientDTO clientDTO) {
        UUID uuid = UUID.fromString(id);
        Optional<Client> clientOptional = clientService.findById(uuid);

        if (clientOptional.isEmpty()) {
            log.warn("Client com ID {} não encontrado para atualização", id);
            return ResponseEntity.notFound().build();
        }

        Client client = clientOptional.get();
        client.setClientId(clientDTO.clientId());
        client.setClientSecret(clientDTO.clientSecret());
        client.setRedirectURI(clientDTO.redirectURI());

        clientService.update(client);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Deletar", description = "Deletar um client")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Client não encontrado na base"),
            @ApiResponse(responseCode = "204", description = "Client deletado com sucesso")
    })
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        log.info("Deletando Client com ID: {} ", id);
        UUID clientId = UUID.fromString(id);
        Optional<Client> clientOptional = clientService.findById(clientId);

        if (clientOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        clientService.delete(clientOptional.get());
        return ResponseEntity.noContent().build();
    }

}
