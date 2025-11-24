package br.com.fiap.gs_witchen_java.service;

import br.com.fiap.gs_witchen_java.config.RabbitConfig;
import br.com.fiap.gs_witchen_java.service.ComandaClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
@Service
public class PlanejamentoService {

    private final ComandaClient comandaClient;
    private final ChatClient chatClient;
    private final RabbitTemplate rabbitTemplate;

    public PlanejamentoService(ComandaClient comandaClient, ChatClient chatClient, RabbitTemplate rabbitTemplate) {
        this.comandaClient = comandaClient;
        this.chatClient = chatClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void gerarPlanoPreparo(int comandaId) {
        // 1. Buscar pedidos da comanda no .NET
        var pedidos = comandaClient.listarPedidos(comandaId);

        // 2. Gerar plano de preparo com IA
        String planoJson = chatClient.prompt()
                .user("Pedidos da comanda " + comandaId + ": " + pedidos.toString())
                .call()
                .content();

        // 3. Publicar na fila RabbitMQ
        rabbitTemplate.convertAndSend("planos-preparo", planoJson);

        System.out.println("📤 Plano publicado na fila: " + planoJson);
    }
}