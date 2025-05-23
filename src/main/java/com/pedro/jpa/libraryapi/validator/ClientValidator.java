package com.pedro.jpa.libraryapi.validator;


import com.pedro.jpa.libraryapi.exceptions.RegistroDuplicadoException;
import com.pedro.jpa.libraryapi.model.Client;
import com.pedro.jpa.libraryapi.repository.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientValidator {
    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void validate(Client client) {
        if (clientAlreadyExists(client)) {
            throw new RegistroDuplicadoException("Client is already registered");
        }
        clientAlreadyExists(client);
    }

    private boolean clientAlreadyExists(Client client) {
        Optional<Client> clientFound = clientRepository.findByClientId(client.getClientId());

        if (client.getId() == null) {
            return clientFound.isPresent();
        }
        return clientFound.isPresent() && !clientFound.get().getId().equals(client.getId());
    }
}