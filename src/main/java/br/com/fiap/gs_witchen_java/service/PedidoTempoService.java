package br.com.fiap.gs_witchen_java.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PedidoTempoService {

    private final Map<Integer, LocalDateTime> criacaoPedidos = new ConcurrentHashMap<>();

    public void registrarPedido(int pedidoId) {
        criacaoPedidos.put(pedidoId, LocalDateTime.now());
    }

    public long getMinutosEsperando(int pedidoId) {
        LocalDateTime criado = criacaoPedidos.get(pedidoId);
        if (criado == null) return 0;

        return Duration.between(criado, LocalDateTime.now()).toMinutes();
    }

    public void removerPedido(int pedidoId) {
        criacaoPedidos.remove(pedidoId);
    }
}
