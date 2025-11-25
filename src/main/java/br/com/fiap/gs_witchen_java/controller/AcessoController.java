package br.com.fiap.gs_witchen_java.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AcessoController {

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth) {

        boolean isGarcom = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_GARCOM"));

        boolean isCozinha = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_COZINHA"));

        if (isGarcom) {
            return "redirect:/comandas";
        }

        if (isCozinha) {
            return "redirect:/cozinha";
        }

        return "redirect:/login";
    }

    @GetMapping("/erro/403")
    public String erro403() {
        return "erros/403";
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {

        model.addAttribute("mensagem", "Erro interno: " + ex.getMessage());

        return "error/500";
    }
    @GetMapping("/erro/404")
    public String erro404() {
        return "erros/404";
    }
}
