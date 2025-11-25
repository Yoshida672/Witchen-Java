package br.com.fiap.gs_witchen_java.controller;

import br.com.fiap.gs_witchen_java.dto.ItemCozinhaDTO;
import br.com.fiap.gs_witchen_java.dto.PedidoCozinhaDTO;
import br.com.fiap.gs_witchen_java.entity.Pedido;
import br.com.fiap.gs_witchen_java.repository.PedidoRepository;
import br.com.fiap.gs_witchen_java.service.PedidoTempoService;
import br.com.fiap.gs_witchen_java.service.WalterAIService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cozinha")
public class CozinhaController {

    private final PedidoRepository pedidoRepository;
    private final WalterAIService walterAIService;
    private final PedidoTempoService pedidoTempoService;

    public CozinhaController(PedidoRepository pedidoRepository,
                             WalterAIService walterAIService,
                             PedidoTempoService pedidoTempoService) {
        this.pedidoRepository = pedidoRepository;
        this.walterAIService = walterAIService;
        this.pedidoTempoService = pedidoTempoService;
    }

    @GetMapping("/tempo/{id}")
    @ResponseBody
    public long tempoPedido(@PathVariable int id) {
        return pedidoTempoService.getMinutosEsperando(id);
    }

    @GetMapping
    public String painelCozinha(Model model) {

        var pedidos = pedidoRepository.findByStatusIgnoreCase("EM ANDAMENTO");

        var pedidosIA = pedidos.stream().map(pedido -> {

            long tempo = pedidoTempoService.getMinutosEsperando(pedido.getId());

            List<ItemCozinhaDTO> itens = pedido.getItens().stream()
                    .map(item -> new ItemCozinhaDTO(
                            item.getProduto().getNomeProduto(),
                            item.getQuantidade(),
                            item.getProduto().getIngredientesProduto()
                    ))
                    .toList();

            String jsonPedido = gerarJsonPedidoCompleto(pedido, itens, tempo);

            String planoIA = walterAIService.analisarPedido(jsonPedido);

            return new PedidoCozinhaDTO(
                    pedido.getId(),
                    "Mesa " + pedido.getComanda().getMesa().getNumero(),
                    tempo,
                    planoIA,
                    itens
            );

        }).toList();

        model.addAttribute("pedidos", pedidosIA);

        return "cozinhas/cozinha-walter";
    }
    @PostMapping("/finalizar")
    public String finalizarPedido(@RequestParam int id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));


        if ("Finalizado".equalsIgnoreCase(pedido.getStatus())) {
            return "redirect:/cozinha";
        }

        pedido.setStatus("FINALIZADO");
        pedidoTempoService.removerPedido(id);

        pedidoRepository.save(pedido);

        return "redirect:/cozinha";
    }

    @GetMapping("/plano/{id}")
    public String planoIA(@PathVariable int id, Model model) {

        var pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        long tempo = pedidoTempoService.getMinutosEsperando(pedido.getId());

        var itens = pedido.getItens().stream()
                .map(item -> new ItemCozinhaDTO(
                        item.getProduto().getNomeProduto(),
                        item.getQuantidade(),
                        item.getProduto().getIngredientesProduto()
                ))
                .toList();

        String jsonPedido = gerarJsonPedidoCompleto(pedido, itens, tempo);

        String planoIA = walterAIService.analisarPedido(jsonPedido);

        PedidoCozinhaDTO dto = new PedidoCozinhaDTO(
                pedido.getId(),
                "Mesa " + pedido.getComanda().getMesa().getNumero(),
                tempo,
                planoIA,
                itens
        );

        model.addAttribute("pedido", dto);

        return "cozinhas/telao-cozinha";
    }

    private String gerarJsonPedidoCompleto(Pedido pedido,
                                           List<ItemCozinhaDTO> itens,
                                           long minutos) {

        String itensFormatados = itens.stream()
                .map(item -> """
                {
                   "produto": "%s",
                   "quantidade": %d,
                   "ingredientes": "%s"
                }
                """.formatted(
                        item.nomeProduto(),
                        item.quantidade(),
                        item.ingradientes()
                ))
                .collect(Collectors.joining(","));

        return """
        {
          "idPedido": %d,
          "mesa": %d,
          "tempoEsperando": %d,
          "itens": [
            %s
          ]
        }
        """.formatted(
                pedido.getId(),
                pedido.getComanda().getMesa().getNumero(),
                minutos,
                itensFormatados
        );
    }
}
