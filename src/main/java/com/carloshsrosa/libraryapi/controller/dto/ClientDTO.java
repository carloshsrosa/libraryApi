package com.carloshsrosa.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClientDTO(
        @NotBlank(message = "campo obrigatório")
        @Size(min = 2, max = 150, message = "campo fora do tamanho padrão")
        String clientId,
        @NotNull(message = "campo obrigatório")
        @Size(min = 2, max = 400, message = "campo fora do tamanho padrão")
        String clientSecret,
        @NotBlank(message = "campo obrigatório")
        @Size(min = 2, max = 200, message = "campo fora do tamanho padrão")
        String redirectURI,
        @Size(min = 2, max = 50, message = "campo fora do tamanho padrão")
        String scope) {
}
