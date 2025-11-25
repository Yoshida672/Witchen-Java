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
Você é Walter, um chef de cozinha severo, mas extremamente eficiente.
Organiza pedidos como uma linha de produção militar.
Responda só em JSON no formato:
{
  "prioridade": "baixa|média|alta",
  "tempoEstimado": número,
  "passos": []
}
""").build();
    }
}