package com.carloshsrosa.libraryapi.controller;

import com.carloshsrosa.libraryapi.controller.dto.ClientDTO;
import com.carloshsrosa.libraryapi.controller.mappers.ClientMapper;
import com.carloshsrosa.libraryapi.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvar(@RequestBody ClientDTO clientDTO){
        var client = mapper.toEntity(clientDTO);
        service.salvar(client);
    }
}
