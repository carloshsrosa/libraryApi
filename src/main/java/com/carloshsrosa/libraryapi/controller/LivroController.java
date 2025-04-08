package com.carloshsrosa.libraryapi.controller;

import com.carloshsrosa.libraryapi.controller.dto.CadastroLivroDTO;
import com.carloshsrosa.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.carloshsrosa.libraryapi.controller.mappers.LivroMapper;
import com.carloshsrosa.libraryapi.model.GeneroLivro;
import com.carloshsrosa.libraryapi.model.Livro;
import com.carloshsrosa.libraryapi.service.AutorService;
import com.carloshsrosa.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("livros")
public class LivroController implements GenericController {

    @Autowired
    private LivroService service;

    @Autowired
    private AutorService autorService;

    @Autowired
    private LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> cadastraLivro(@RequestBody @Valid CadastroLivroDTO livroDTO){
        var livro = mapper.toEntity(livroDTO);
        service.salvarLivro(livro);
        var uri = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> consultaPorId(@PathVariable("id") String id){
        return service
                .consultarPorId(UUID.fromString(id))
                .map(livro ->
                        mapper.toDTO(livro))
                .map(ResponseEntity::ok)
                .orElseGet( () -> { return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> consultarPorParametros(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "nome-autor", required = false) String nomeAutor,
            @RequestParam(value = "genero", required = false) GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ){
        var livros = service.consultarPorParametros(isbn, titulo, nomeAutor, genero, anoPublicacao, page, size);

        var resultado = livros.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualiza(
            @PathVariable("id") String id,
            @RequestBody @Valid CadastroLivroDTO livroDTO){
        return service
                .consultarPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidade = mapper.toEntity(livroDTO);
                    livro.setIsbn(entidade.getIsbn());
                    livro.setPreco(entidade.getPreco());
                    livro.setGenero(entidade.getGenero());
                    livro.setTitulo(entidade.getTitulo());
                    livro.setAutor(entidade.getAutor());
                    livro.setDataPublicacao(entidade.getDataPublicacao());

                    service.atualiza(livro);

                    return ResponseEntity.ok(mapper.toDTO(livro));
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delelaLivro(@PathParam("id") String id) {
        return service.consultarPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletarPorId(livro.getId());
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
