package com.carloshsrosa.libraryapi.validator;

import com.carloshsrosa.libraryapi.repository.AutorRepository;
import com.carloshsrosa.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AutorPossuiLivrosValidator {

    private final AutorRepository autorRepository;

    private final LivroRepository livroRepository;

    public boolean validar(UUID uuid){
        var autor = autorRepository.findById(uuid);
        if (autor.isPresent()) {
            return livroRepository.existsByAutor(autor);
        }
        return false;
    }

}