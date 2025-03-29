package com.carloshsrosa.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTOResposta(UUID id, String nome, LocalDate dataNascimento, String nacionalidade) {
}
