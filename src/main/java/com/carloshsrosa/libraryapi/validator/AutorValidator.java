package com.carloshsrosa.libraryapi.validator;

import com.carloshsrosa.libraryapi.exceptions.RegistroDuplicadoException;
import com.carloshsrosa.libraryapi.model.Autor;
import com.carloshsrosa.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutorValidator {

    @Autowired
    private AutorRepository repository;

    public boolean validar(Autor autor){
        var autorExistente = repository.findByNomeContainingIgnoreCaseAndDataNascimentoAndNacionalidadeContainingIgnoreCase(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
        if (autor.getId() == null) {
            return autorExistente.isPresent();
        }

        return !(autor.getId().equals(autorExistente.get().getId())) && autorExistente.isPresent();
    }

}