package com.carloshsrosa.libraryapi.controller;

import com.carloshsrosa.libraryapi.controller.dto.UsuarioDTO;
import com.carloshsrosa.libraryapi.controller.mappers.UsuarioMapper;
import com.carloshsrosa.libraryapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        var usuario = mapper.toEntity(usuarioDTO);
        service.salvarUsuario(usuario);
    }
}
