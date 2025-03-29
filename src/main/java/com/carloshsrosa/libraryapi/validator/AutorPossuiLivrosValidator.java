package com.carloshsrosa.libraryapi.validator;

import com.carloshsrosa.libraryapi.model.Autor;
import com.carloshsrosa.libraryapi.repository.AutorRepository;
import com.carloshsrosa.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AutorPossuiLivrosValidator {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    public boolean validar(UUID uuid){
        var autor = autorRepository.findById(uuid);
        if (autor.isPresent()) {
            return livroRepository.existsByAutor(autor);
        }
        return false;
    }

}