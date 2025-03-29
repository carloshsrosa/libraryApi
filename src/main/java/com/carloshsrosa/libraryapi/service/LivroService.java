package com.carloshsrosa.libraryapi.service;

import com.carloshsrosa.libraryapi.exceptions.RegistroDuplicadoException;
import com.carloshsrosa.libraryapi.model.GeneroLivro;
import com.carloshsrosa.libraryapi.model.Livro;
import com.carloshsrosa.libraryapi.repository.LivroRepository;
import com.carloshsrosa.libraryapi.validator.LivroValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.carloshsrosa.libraryapi.repository.specs.LivroSpecs.*;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private LivroValidator validator;

    public Livro salvarLivro(Livro livro) {
        if (validator.validar(livro)) {
            throw new RegistroDuplicadoException("Livro já existente");
        }
        return repository.save(livro);
    }

    public Optional<Livro> consultarPorId(UUID uuid) {
        return repository.findById(uuid);
    }

    public void deletarPorId(UUID uuid) {
        repository.deleteById(uuid);
    }

    public List<Livro> consultarPorParametros(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao){

        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }

        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if (genero != null){
            specs = specs.and(generoEqual(genero));
        }

        if (anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if (nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        return repository.findAll(specs);
    }

    public void atualiza(Livro livro) {
        if (livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o livro já esteja na cadastrado!");
        }
        if (validator.validarIsbn(livro)) {
            throw new RegistroDuplicadoException("ISBN pertence a outro livro");
        }
        repository.save(livro);
    }
}
