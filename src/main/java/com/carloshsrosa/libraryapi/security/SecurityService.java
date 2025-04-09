package com.carloshsrosa.libraryapi.security;

import com.carloshsrosa.libraryapi.model.Usuario;
import com.carloshsrosa.libraryapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    @Autowired
    private UsuarioService usuarioService;

    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var usuarioLogado = (UserDetails) authentication.getPrincipal();
        var login = usuarioLogado.getUsername();
        return usuarioService.obterLogin(login);
    }
}
