package com.carloshsrosa.libraryapi.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroRespostaDTO(int status, String mensagem, List<ErroCampoDTO> erros) {

    public static ErroRespostaDTO respostaPadrao(String mensagem){
        return new ErroRespostaDTO(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErroRespostaDTO respostaConflito(String mensagem){
        return new ErroRespostaDTO(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }
}
