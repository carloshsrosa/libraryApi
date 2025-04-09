package com.carloshsrosa.libraryapi.controller;

import com.carloshsrosa.libraryapi.controller.dto.AutorDTO;
import com.carloshsrosa.libraryapi.controller.dto.AutorDTOResposta;
import com.carloshsrosa.libraryapi.controller.mappers.AutorMapper;
import com.carloshsrosa.libraryapi.controller.mappers.AutorMapperResposta;
import com.carloshsrosa.libraryapi.security.SecurityService;
import com.carloshsrosa.libraryapi.service.AutorService;
import com.carloshsrosa.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("autores")
public class AutorController implements GenericController {

    @Autowired
    private AutorService service;

    @Autowired
    private AutorMapper mapper;

    @Autowired
    private AutorMapperResposta mapperResposta;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> salvarAutor(@RequestBody @Valid AutorDTO autorDTO) {
        var autor = mapper.toEntity(autorDTO);
        service.salvarAutor(autor);
        var uri = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<AutorDTOResposta> consultarPorId(@PathVariable("id") String id) {
        var uuid = UUID.fromString(id);
        return service
                .consultarPorId(uuid)
                .map(autor -> {
                    AutorDTOResposta autorDTOResposta = mapperResposta.toDTO(autor);
                    return ResponseEntity.ok(autorDTOResposta);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<AutorDTOResposta>> consultarPorParametros(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        var autores = service.consultarPorParametros(nome, nacionalidade);
        if (autores == null) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        List<AutorDTOResposta> autoresDTOResposta = autores
                .stream()
                .map(mapperResposta::toDTO)
                .toList();

        return ResponseEntity.ok(autoresDTOResposta);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<AutorDTOResposta> atualiza(@PathVariable("id") String id, @RequestBody @Valid AutorDTO autorDTO) {
        return service
                .consultarPorId(UUID.fromString(id))
                .map(autor -> {
                    var autorAtualizado = mapper.toEntity(autorDTO);
                    autorAtualizado.setId(autor.getId());
                    service.atualiza(autorAtualizado);
                    return ResponseEntity.ok(mapperResposta.toDTO(autorAtualizado));
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> deletarPorId(@PathVariable("id") String id) {
        return service.consultarPorId(UUID.fromString(id))
                .map(autor -> {
                    service.deletarPorId(autor.getId());
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
