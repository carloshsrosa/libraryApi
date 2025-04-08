package com.carloshsrosa.libraryapi.service;

import com.carloshsrosa.libraryapi.exceptions.AutorPossuiLivrosException;
import com.carloshsrosa.libraryapi.exceptions.RegistroDuplicadoException;
import com.carloshsrosa.libraryapi.model.Autor;
import com.carloshsrosa.libraryapi.repository.AutorRepository;
import com.carloshsrosa.libraryapi.validator.AutorPossuiLivrosValidator;
import com.carloshsrosa.libraryapi.validator.AutorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@RequiredArgsConstructor -- loombok
@Service
public class AutorService {

    @Autowired
    private AutorRepository repository;

    @Autowired
    private AutorValidator validator;

    @Autowired
    private AutorPossuiLivrosValidator livrosValidator;

    public Autor salvarAutor(Autor autor) {
        if (validator.validar(autor)){
            throw new RegistroDuplicadoException("Autor já existente");
        }
        return repository.save(autor);
    }

    public Optional<Autor> consultarPorId(UUID uuid) {
        return repository.findById(uuid);
    }

    public void deletarPorId(UUID uuid) {
        if (livrosValidator.validar(uuid)){
            throw new AutorPossuiLivrosException("Não é permitido deletar autor que possui livros");
        }
        repository.deleteById(uuid);
    }

    public List<Autor> consultarPorParametros(String nome, String nacionalidade) {
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "dataNascimento")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, matcher);
        return repository.findAll(autorExample);
    }

    public void atualiza(Autor autor) {
        if (autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja na cadastrado!");
        }
        validator.validar(autor);
        repository.save(autor);
    }
}
