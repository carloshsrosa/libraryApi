package com.carloshsrosa.libraryapi.service;

import com.carloshsrosa.libraryapi.model.Autor;
import com.carloshsrosa.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AutorService {
    @Autowired
    private AutorRepository repository;

    public Autor salvarAutor(Autor autor){
        return repository.save(autor);
    }

    public Autor consultarPorId(UUID uuid) {
        return repository.findById(uuid).orElse(null);
    }

    public void deletarPorId(UUID uuid) {
        repository.deleteById(uuid);
    }

    public List<Autor> consultarPorParametros(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null){
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
        repository.save(autor);
    }
}
