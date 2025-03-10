package com.carloshsrosa.libraryapi.service;

import com.carloshsrosa.libraryapi.exceptions.AutorPossuiLivrosException;
import com.carloshsrosa.libraryapi.exceptions.RegistroDuplicadoException;
import com.carloshsrosa.libraryapi.model.Autor;
import com.carloshsrosa.libraryapi.repository.AutorRepository;
import com.carloshsrosa.libraryapi.validator.AutorPossuiLivrosValidator;
import com.carloshsrosa.libraryapi.validator.AutorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    public Autor consultarPorId(UUID uuid) {
        return repository.findById(uuid).orElse(null);
    }

    public void deletarPorId(UUID uuid) {
        if (livrosValidator.validar(uuid)){
            throw new AutorPossuiLivrosException("Não é permitido deletar autor que possui livros");
        }
        repository.deleteById(uuid);
    }

    public List<Autor> consultarPorParametros(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return repository.findByNomeContainingIgnoreCaseAndNacionalidadeContainingIgnoreCase(nome, nacionalidade);
        }
        if (nome != null) {
            return repository.findByNomeContainingIgnoreCase(nome);
        }
        if (nacionalidade != null) {
            return repository.findByNacionalidadeContainingIgnoreCase(nacionalidade);
        }

        return repository.findAll();
    }

    public void atualiza(Autor autor) {
        if (validator.validar(autor)){
            throw new RegistroDuplicadoException("Autor já existente");
        }
        repository.save(autor);
    }
}
