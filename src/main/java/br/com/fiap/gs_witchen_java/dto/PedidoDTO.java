package br.com.fiap.gs_witchen_java.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
public record PedidoDTO(
        @NotNull(message = "A comanda é obrigatória")

        Integer comandaId,
        @NotEmpty(message = "O pedido deve ter pelo menos um item")

        List<ItemPedidoDTO> itens) {
}

