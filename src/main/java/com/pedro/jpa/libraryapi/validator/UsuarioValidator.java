package com.pedro.jpa.libraryapi.validator;

import com.pedro.jpa.libraryapi.exceptions.RegistroDuplicadoException;
import com.pedro.jpa.libraryapi.model.Usuario;
import com.pedro.jpa.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public void validate(Usuario usuario) {
        if (userAlreadyExists(usuario)) {
            throw new RegistroDuplicadoException("User is already registered");
        }

    }

    private boolean userAlreadyExists(Usuario usuario) {
        Optional<Usuario> userFound = Optional.ofNullable(usuarioRepository.findTopByEmailOrLogin(usuario.getEmail(), usuario.getLogin()));

        if (usuario.getId() == null) {
            return userFound.isPresent();
        }

        return userFound.isPresent() && !userFound.get().getId().equals(usuario.getId());
    }
}
