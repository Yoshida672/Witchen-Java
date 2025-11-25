package br.com.fiap.gs_witchen_java.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;



@Service
public class WalterAIService {

    private final ChatClient chatClient;

    public WalterAIService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String analisarPedido(String pedidoCompleto) {

        String resposta = chatClient
                .prompt()
                .user("""
                Você é um chefe de cozinha de restaurante.

                Seu trabalho é organizar a preparação dos pedidos para a equipe.

                Aqui estão os dados do pedido:
                %s

                Gere um plano de preparo seguindo estas regras:
                - Liste em steps numerados
                - Seja direto e prático
                - Considere tempo e ingredientes
                - Priorize organização e velocidade
                """.formatted(pedidoCompleto))
                .call()
                .content();

        return resposta;
    }
}
