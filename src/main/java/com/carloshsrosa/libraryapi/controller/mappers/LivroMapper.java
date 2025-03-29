package com.carloshsrosa.libraryapi.controller.mappers;

import com.carloshsrosa.libraryapi.controller.dto.CadastroLivroDTO;
import com.carloshsrosa.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.carloshsrosa.libraryapi.model.Livro;
import com.carloshsrosa.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository repository;

    @Mapping(target = "autor", expression = "java( repository.findById(livroDTO.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO livroDTO);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
