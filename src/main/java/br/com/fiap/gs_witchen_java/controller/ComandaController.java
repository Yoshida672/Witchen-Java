package br.com.fiap.gs_witchen_java.controller;

import br.com.fiap.gs_witchen_java.dto.ComandaDTO;
import br.com.fiap.gs_witchen_java.dto.ItemPedidoDTO;
import br.com.fiap.gs_witchen_java.dto.PedidoDTO;
import br.com.fiap.gs_witchen_java.entity.Comanda;
import br.com.fiap.gs_witchen_java.service.ComandaService;
import br.com.fiap.gs_witchen_java.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comandas")
public class ComandaController {

    private final ComandaService comandaService;
    private final ProdutoService produtoService;

    public ComandaController(ComandaService comandaService, ProdutoService produtoService) {
        this.comandaService = comandaService;
        this.produtoService = produtoService;
    }

    @GetMapping("/novo")
    public String formNovaComanda(Model model) {
        model.addAttribute("comanda", new ComandaDTO(null));
        return "comandas/comanda_form";
    }

    @PostMapping("/novo")
    public String criarComanda(
            @Valid @ModelAttribute("comanda") ComandaDTO dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "comandas/comanda_form";
        }

        try {
            comandaService.criarComanda(dto.mesaId());
            return "redirect:/comandas";

        } catch (IllegalStateException e) {
            model.addAttribute("erro", e.getMessage());
            return "comandas/comanda_form";
        }
    }

    @GetMapping("/pedidos/novo")
    public String formNovoPedido(@RequestParam Integer comandaId, Model model) {
        var produtos = produtoService.getTodosProdutos();

        List<ItemPedidoDTO> itens = produtos.stream()
                .map(prod -> new ItemPedidoDTO(prod.id(), 0))
                .toList();

        PedidoDTO pedido = new PedidoDTO(comandaId, itens);

        model.addAttribute("pedido", pedido);
        model.addAttribute("comandaId", comandaId);
        model.addAttribute("produtos", produtos);

        return "pedidos/pedido_form";
    }



    @PostMapping("/pedidos/novo")
    public String adicionarPedido(
            @RequestParam Integer comandaId,
            @Valid @ModelAttribute("pedido") PedidoDTO dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("comandaId", comandaId);
            return "pedidos/pedido_form";
        }

        try {
            comandaService.adicionarPedido(comandaId, dto);
            return "redirect:/comandas";

        } catch (IllegalStateException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("comandaId", comandaId);
            return "pedidos/pedido_form";
        }
    }

    @GetMapping
    public String listarComandas(Model model) {
        model.addAttribute("comandas", comandaService.getComandasAtivas());
        return "comandas/comanda_list";
    }
    @GetMapping("/fechar")
    public String fecharComanda(@RequestParam Integer id, Model model) {
            Comanda comanda = comandaService.getById(id);

        if (comanda.calcularTotal() <= 0) {
            model.addAttribute("erro", "Não é possível fechar uma comanda com valor menor ou igual a zero.");
            List<Comanda> comandas = comandaService.getComandasAtivas();
            model.addAttribute("comandas", comandas);
            return "comandas/comanda_list";
        }

        comandaService.fecharComanda(id, "Dinheiro");
        return "redirect:/comandas";
    }

}
