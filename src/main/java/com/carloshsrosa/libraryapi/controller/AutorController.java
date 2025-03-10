package com.carloshsrosa.libraryapi.controller;

import com.carloshsrosa.libraryapi.controller.dto.AutorDTO;
import com.carloshsrosa.libraryapi.controller.dto.AutorDTOResposta;
import com.carloshsrosa.libraryapi.model.Autor;
import com.carloshsrosa.libraryapi.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("autores")
public class AutorController {

    @Autowired
    private AutorService service;

    @PostMapping
    public ResponseEntity<Void> salvarAutor(@RequestBody AutorDTO autorDTO){
        var autor = service.salvarAutor(autorDTO.mapearAutor());
        if (autor == null){
            return ResponseEntity.badRequest().build();
        }
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autor.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTOResposta> consultarPorId(@PathVariable("id") String id){
        var uuid = UUID.fromString(id);
        var autor = service.consultarPorId(uuid);
        if (autor == null){
            return ResponseEntity.notFound().build();
        }
        var autorDTOResposta = new AutorDTOResposta(
                autor.getId(),
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade());
        return ResponseEntity.ok().body(autorDTOResposta);
    }

    @GetMapping
    public ResponseEntity<List<AutorDTOResposta>> consultarPorParametros(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<Autor> autores = service.consultarPorParametros(nome, nacionalidade);
        if (autores == null){
            return ResponseEntity.ok().body(new ArrayList<>());
        }
        List<AutorDTOResposta> autoresDTOResposta = autores
                .stream()
                .map(a -> new AutorDTOResposta(
                        a.getId(),
                        a.getNome(),
                        a.getDataNascimento(),
                        a.getNacionalidade()))
                .toList();

        return ResponseEntity.ok().body(autoresDTOResposta);
    }

    @PutMapping("{id}")
    public ResponseEntity<AutorDTOResposta> atualiza(@PathVariable("id") String id, @RequestBody AutorDTO autorDTO){
        var uuid = UUID.fromString(id);
        var autor = service.consultarPorId(uuid);
        if (autor == null){
            return ResponseEntity.notFound().build();
        }
        autor.setNome(autorDTO.nome());
        autor.setDataNascimento(autorDTO.dataNascimento());
        autor.setNacionalidade(autorDTO.nacionalidade());

        service.atualiza(autor);

        var autorDTOResposta = new AutorDTOResposta(
                autor.getId(),
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade());

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autor.getId())
                .toUri();

        return ResponseEntity.ok(autorDTOResposta);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable("id") String id){
        var uuid = UUID.fromString(id);
        var autor = service.consultarPorId(uuid);
        if (autor == null){
            return ResponseEntity.notFound().build();
        }
        service.deletarPorId(uuid);
        return ResponseEntity.noContent().build();
    }
}
