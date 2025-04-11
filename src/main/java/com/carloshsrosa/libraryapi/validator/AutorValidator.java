package com.carloshsrosa.libraryapi.validator;

import com.carloshsrosa.libraryapi.model.Autor;
import com.carloshsrosa.libraryapi.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AutorValidator {

    private final AutorRepository repository;

    public boolean validar(Autor autor){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "dataNascimento")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, matcher);
        List<Autor> listAutor = repository.findAll(autorExample);
        return !listAutor.isEmpty();
    }

}