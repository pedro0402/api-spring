package com.pedro.jpa.libraryapi.service;

import com.pedro.jpa.libraryapi.model.Client;
import com.pedro.jpa.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public Client salvar(Client client) {
        //seria ideal criar uma validação de cadastro de clients aqui
        String senha = client.getClientSecret();
        client.setClientSecret(passwordEncoder.encode(senha)); //criando a senha criptografada no banco
        return clientRepository.save(client);
    }

    public Client obterPorClientId(String clientId) {
        return clientRepository.findByClientId(clientId);
    }

}
