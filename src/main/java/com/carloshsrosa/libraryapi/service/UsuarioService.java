package com.carloshsrosa.libraryapi.service;

import com.carloshsrosa.libraryapi.model.Usuario;
import com.carloshsrosa.libraryapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public void salvarUsuario(Usuario usuario){
        var senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));
        repository.save(usuario);
    }

    public Usuario obterLogin(String login){
        return repository.findByLogin(login);
    }
}
