package br.com.fiap.gs_witchen_java.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ComandaDTO(
        @NotNull(message = "O id da mesa é obrigatório")
        @Positive(message = "O id da mesa deve ser positivo")
        Integer mesaId) { }

