package com.carloshsrosa.libraryapi.controller.mappers;

import com.carloshsrosa.libraryapi.controller.dto.UsuarioDTO;
import com.carloshsrosa.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO usuarioDTO);
}
