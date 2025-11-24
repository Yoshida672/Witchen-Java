package br.com.fiap.gs_witchen_java.service;

import br.com.fiap.gs_witchen_java.entity.Comanda;
import br.com.fiap.gs_witchen_java.entity.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ComandaClient {

    private final WebClient webClient;

    public ComandaClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://witchen.azurewebsites.net/api/v1/comanda").build();
    }

    public Comanda abrirComanda(int mesaId) {
        return webClient.post()
                .uri("/abrir/{mesaId}", mesaId)
                .retrieve()
                .bodyToMono(Comanda.class)
                .block();
    }

    public Comanda obterComandaAtiva(int mesaId) {
        return webClient.get()
                .uri("/ativa/{mesaId}", mesaId)
                .retrieve()
                .bodyToMono(Comanda.class)
                .block();
    }

    public List<Pedido> listarPedidos(int comandaId) {
        return webClient.get()
                .uri("/{comandaId}/pedidos", comandaId)
                .retrieve()
                .bodyToFlux(Pedido.class)
                .collectList()
                .block();
    }
}