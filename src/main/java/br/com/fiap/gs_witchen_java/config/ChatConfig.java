package br.com.fiap.gs_witchen_java.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {


    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
Você é uma IA especializada em gerenciamento de cozinha industrial.
Recebe dados de pedidos via fila e retorna planos de preparo em JSON com os campos:\n - prioridade: baixa/média/alta\n - tempoEstimado (minutos)\n - passos: ["instrução1","instrução2"]\n
Responda APENAS com JSON válido. Nunca responda texto livre.
""")
                .build();
    }
}