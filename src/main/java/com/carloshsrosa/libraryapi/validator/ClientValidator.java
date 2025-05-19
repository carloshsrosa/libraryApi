package com.carloshsrosa.libraryapi.validator;

import com.carloshsrosa.libraryapi.model.Autor;
import com.carloshsrosa.libraryapi.model.Client;
import com.carloshsrosa.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientValidator {

    private final ClientRepository repository;

    public boolean validar(Client client){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("clientSecret", "redirectURI", "scope")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Client> clientExample = Example.of(client, matcher);
        List<Client> listClient = repository.findAll(clientExample);
        return !listClient.isEmpty();
    }
}
