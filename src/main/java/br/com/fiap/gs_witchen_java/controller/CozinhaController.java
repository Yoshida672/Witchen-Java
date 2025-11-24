package br.com.fiap.gs_witchen_java.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cozinha")
public class CozinhaController {

    private final List<String> planosRecebidos = new ArrayList<>();

    @RabbitListener(queues = "planos-preparo")
    public void receberPlano(String planoJson) {
        planosRecebidos.add(planoJson);
        System.out.println("📦 Plano recebido na cozinha: " + planoJson);
    }

    @GetMapping
    public String listarPlanos(Model model) {
        model.addAttribute("planos", planosRecebidos);
        return "cozinha-dashboard";
    }
}