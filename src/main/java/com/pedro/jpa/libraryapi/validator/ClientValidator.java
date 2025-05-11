package com.pedro.jpa.libraryapi.validator;

import com.pedro.jpa.libraryapi.exceptions.RegistroDuplicadoException;
import com.pedro.jpa.libraryapi.model.Client;
import com.pedro.jpa.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientValidator {

    private final ClientRepository clientRepository;
        
    public void validate(Client client){
        if (clientAlreadyExists(client)){
            throw new RegistroDuplicadoException("Client is already registered");
        }
    }

    private boolean clientAlreadyExists(Client client){
        Optional<Client> clientFound = clientRepository.findByClientId(client.getClientId());

        if (client.getId() == null){
            return clientFound.isPresent();
        }

        return !client.getClientId().equals(clientFound.get().getClientId());
    }

}
