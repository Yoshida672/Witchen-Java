package br.com.fiap.gs_witchen_java.listener;

import br.com.fiap.gs_witchen_java.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CozinhaConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receberPlano(String planoJson) {
        System.out.println("📦 Plano recebido na cozinha: " + planoJson);
    }
}