package com.carloshsrosa.libraryapi.controller.mappers;

import com.carloshsrosa.libraryapi.controller.dto.AutorDTO;
import com.carloshsrosa.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO autorDTO);

    AutorDTO toDTO(Autor autor);
}
