package com.carloshsrosa.libraryapi.controller.mappers;

import com.carloshsrosa.libraryapi.controller.dto.AutorDTOResposta;
import com.carloshsrosa.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapperResposta {

    AutorDTOResposta toDTO(Autor autor);

}
