package com.pedro.jpa.libraryapi.repository;

import com.pedro.jpa.libraryapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByClientId(String clientId);

}
