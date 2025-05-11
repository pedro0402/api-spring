package com.pedro.jpa.libraryapi.service;

import com.pedro.jpa.libraryapi.model.Usuario;
import com.pedro.jpa.libraryapi.repository.UsuarioRepository;
import com.pedro.jpa.libraryapi.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioValidator usuarioValidator;

    public void salvar(Usuario usuario) {
        usuarioValidator.validate(usuario);
        String senha = usuario.getSenha();
        usuario.setSenha(passwordEncoder.encode(senha));
        usuarioRepository.save(usuario);
    }

    public Usuario obterPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public Usuario obterPorEmail(String email) {
       return usuarioRepository.findByEmail(email);
    }
}
