package com.carloshsrosa.libraryapi.controller.dto;

import com.carloshsrosa.libraryapi.model.Autor;
import jakarta.persistence.Column;

import java.time.LocalDate;

public record AutorDTO(String nome, LocalDate dataNascimento, String nacionalidade) {

    public Autor mapearAutor(){
        var autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
