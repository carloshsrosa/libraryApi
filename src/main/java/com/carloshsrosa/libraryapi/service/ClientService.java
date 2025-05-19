package com.carloshsrosa.libraryapi.service;

import com.carloshsrosa.libraryapi.exceptions.RegistroDuplicadoException;
import com.carloshsrosa.libraryapi.model.Client;
import com.carloshsrosa.libraryapi.repository.ClientRepository;
import com.carloshsrosa.libraryapi.validator.ClientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;
    private final ClientValidator validator;

    public Client salvar(Client client){
        if (validator.validar(client)){
            throw new RegistroDuplicadoException("Client já existente");
        }
        var senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);
        return repository.save(client);
    }

    public Client obterClientId(String clientId){
        return repository.findByClientId(clientId);
    }

}
