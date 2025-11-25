package br.com.fiap.gs_witchen_java.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public record ItemPedidoDTO(
        @NotNull(message = "O id do produto é obrigatório")
        @Positive(message = "O id do produto deve ser positivo")
        Integer produtoId,
        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser maior que zero")
        Integer quantidade) {
}