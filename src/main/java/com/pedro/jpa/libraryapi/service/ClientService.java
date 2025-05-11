package com.pedro.jpa.libraryapi.service;

import com.pedro.jpa.libraryapi.model.Client;
import com.pedro.jpa.libraryapi.repository.ClientRepository;
import com.pedro.jpa.libraryapi.validator.ClientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientValidator clientValidator;

    public Client save(Client client) {
        clientValidator.validate(client);
        String senha = client.getClientSecret();
        client.setClientSecret(passwordEncoder.encode(senha));
        return clientRepository.save(client);
    }

    public void update(Client client) {
        if (client.getClientId() == null) {
            throw new IllegalArgumentException("To update, the client must already be in the database");
        }
        clientValidator.validate(client);
        client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
        clientRepository.save(client);
    }

    public Optional<Client> obterPorClientId(String clientId) {
        return clientRepository.findByClientId(clientId);
    }

    public Optional<Client> findById(UUID uuid){
        return clientRepository.findById(uuid);
    }

    public List<Client> findAllClients(){
        return clientRepository.findAll();
    }

    public void delete(Client client) {
        clientRepository.delete(client);
    }

}
