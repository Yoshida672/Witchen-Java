package br.com.fiap.gs_witchen_java.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public record ItemCozinhaDTO(
        @NotBlank(message = "Nome do produto é obrigatório")

        String nomeProduto,
        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser maior que zero")
        int quantidade,
        @NotBlank(message = "Ingredientes não podem ser vazios")

        String ingradientes) {

}
