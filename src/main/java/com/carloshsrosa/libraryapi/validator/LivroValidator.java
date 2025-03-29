package com.carloshsrosa.libraryapi.validator;

import com.carloshsrosa.libraryapi.model.Autor;
import com.carloshsrosa.libraryapi.model.GeneroLivro;
import com.carloshsrosa.libraryapi.model.Livro;
import com.carloshsrosa.libraryapi.repository.AutorRepository;
import com.carloshsrosa.libraryapi.repository.LivroRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class LivroValidator {

    @Autowired
    private LivroRepository repository;

    public boolean validar(Livro livro){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "titulo", "dataPublicacao", "genero", "preco", "autor")
                .withIgnoreNullValues()
                .withMatcher("isbn", ExampleMatcher.GenericPropertyMatchers.exact());

        Example<Livro> livroExample = Example.of(livro, matcher);
        var livroExistente =  repository.findAll(livroExample);
        return !livroExistente.isEmpty();
    }

    public boolean validarIsbn(Livro livro) {
        var livroExistente =  repository.findByIsbn(livro.getIsbn());

        if (livroExistente.isPresent()) {
            return !livro.getId().equals(livroExistente.get().getId());
        }
        return false;
    }
}