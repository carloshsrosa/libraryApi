package com.carloshsrosa.libraryapi.security;

import com.carloshsrosa.libraryapi.model.Usuario;
import com.carloshsrosa.libraryapi.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String SENHA_PADRAO = "123";

    private final UsuarioService usuarioService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        var oAuth2User = oAuth2AuthenticationToken.getPrincipal();

        var email = oAuth2User.getAttribute("email").toString();

        var usuario = usuarioService.obterPorEmail(email);

        if(usuario == null){
            usuario = cadastrarUsuario(email);
        }

        authentication = new CustomAuthentication(usuario);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Usuario cadastrarUsuario(String email) {
        Usuario usuario;
        usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setLogin(obterLoginApartirEmail(email));
        usuario.setSenha(SENHA_PADRAO);
        usuario.setRoles(List.of("OPERADOR"));
        usuarioService.salvarUsuario(usuario);
        return usuario;
    }

    private String obterLoginApartirEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
