package br.com.fiap.gs_witchen_java.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotEmpty;
public record PedidoCozinhaDTO(
        @NotNull(message = "ID do pedido é obrigatório")

        Integer idPedido,
        String mesa,
        @Positive(message = "Minutos esperando não pode ser negativa")

        long minutosEsperando,
        String planoIA,
        @NotEmpty(message = "O pedido deve conter pelo menos 1 item")

        List<ItemCozinhaDTO> itens
) {}