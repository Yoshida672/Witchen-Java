package br.com.fiap.gs_witchen_java.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public record ProdutoDTO(
        @NotNull(message = "ID é obrigatório")

        Integer id,
        @NotBlank(message = "Nome é obrigatório")

        String nome,
        @NotBlank(message = "Ingredientes são obrigatórios")

        String ingredientes,
        @Positive(message = "O preço deve ser maior que zero")

        double preco) {}
