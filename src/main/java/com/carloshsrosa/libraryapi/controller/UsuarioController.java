package com.carloshsrosa.libraryapi.controller;

import com.carloshsrosa.libraryapi.controller.dto.UsuarioDTO;
import com.carloshsrosa.libraryapi.controller.mappers.UsuarioMapper;
import com.carloshsrosa.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO){
        var usuario = mapper.toEntity(usuarioDTO);
        service.salvarUsuario(usuario);
    }
}
