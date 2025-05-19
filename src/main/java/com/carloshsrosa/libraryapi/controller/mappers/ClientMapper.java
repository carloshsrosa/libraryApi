package com.carloshsrosa.libraryapi.controller.mappers;

import com.carloshsrosa.libraryapi.controller.dto.ClientDTO;
import com.carloshsrosa.libraryapi.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDTO clientDTO);

    ClientDTO toDTO(Client client);
}
