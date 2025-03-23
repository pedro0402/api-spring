package com.pedro.jpa.libraryapi.service;

import com.pedro.jpa.libraryapi.model.Client;
import com.pedro.jpa.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client salvar(Client client){
        return clientRepository.save(client);
    }

    public Client obterPorClientId(String clientId){
        return clientRepository.findByClientId(clientId);
    }

}
