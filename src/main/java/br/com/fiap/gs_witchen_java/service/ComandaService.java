package br.com.fiap.gs_witchen_java.service;

import br.com.fiap.gs_witchen_java.dto.ItemPedidoDTO;
import br.com.fiap.gs_witchen_java.dto.PedidoDTO;
import br.com.fiap.gs_witchen_java.entity.Comanda;

import br.com.fiap.gs_witchen_java.entity.ItemPedido;
import br.com.fiap.gs_witchen_java.entity.Pedido;
import br.com.fiap.gs_witchen_java.repository.ComandaRepository;
import br.com.fiap.gs_witchen_java.repository.ItemPedidoRepository;
import br.com.fiap.gs_witchen_java.repository.MesaRepository;
import br.com.fiap.gs_witchen_java.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComandaService {

    private final ComandaRepository comandaRepository;
    private final MesaRepository mesaRepository;
    private final CozinhaService cozinhaService;
    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoTempoService pedidoTempoService;
    public ComandaService(ComandaRepository comandaRepository, MesaRepository mesaRepository, CozinhaService cozinhaService, PedidoRepository pedidoRepository, ItemPedidoRepository itemPedidoRepository, PedidoTempoService pedidoTempoService) {
        this.comandaRepository = comandaRepository;
        this.mesaRepository = mesaRepository;
        this.cozinhaService = cozinhaService;
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoTempoService = pedidoTempoService;
        this.cozinhaService.iniciarProcessamento();
    }

    public void criarComanda(Integer mesaId) {
        if (mesaId == null) {
            throw new IllegalArgumentException("Você precisa selecionar uma mesa.");
        }

        if (!mesaRepository.existsById(mesaId)) {
            throw new IllegalStateException("Mesa selecionada não existe.");
        }

        if (!comandaRepository.findByMesaIdAndStatusIgnoreCase(mesaId, "Aberta").isEmpty()) {
            throw new IllegalStateException("Já existe uma comanda aberta para esta mesa.");

        }

        Comanda comanda = Comanda.builder()
                .mesaId(mesaId)
                .status("Aberta")
                .dataAbertura(LocalDateTime.now())
                .build();

        comandaRepository.save(comanda);
    }
    @Transactional
    public void adicionarPedido(Integer comandaId, PedidoDTO dto) {

        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new IllegalArgumentException("Comanda não encontrada"));

        Pedido pedido = new Pedido();
        pedido.setComandaId(comandaId);
        pedido.setStatus("EM ANDAMENTO");

        pedidoRepository.save(pedido);
        pedidoTempoService.registrarPedido(pedido.getId());

        for (ItemPedidoDTO itemDTO : dto.itens()) {
            ItemPedido item = new ItemPedido();
            item.setPedidoId(pedido.getId());
            item.setProdutoId(itemDTO.produtoId());
            item.setComandaId(comandaId);
            item.setQuantidade(itemDTO.quantidade());

            itemPedidoRepository.save(item);
        }
    }


    public List<Comanda> getComandasAtivas() {
        return comandaRepository.findByStatusIgnoreCase("Aberta");
    }

    @Cacheable(value = "comandas", key = "#id")

    public Comanda getById(Integer id) {
        return comandaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));
    }


    @Transactional
    public void fecharComanda(Integer comandaId, String metodoPagamento) {

        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new IllegalStateException("Comanda não encontrada"));

        List<Pedido> pedidos = pedidoRepository.findByComandaId(comandaId);

        for (Pedido pedido : pedidos) {
            pedido.setStatus("FINALIZADO");
            pedidoRepository.save(pedido);
            pedidoTempoService.removerPedido(pedido.getId());
        }

        comanda.fecharComanda(metodoPagamento);
        comanda.setStatus("FECHADA");

        comandaRepository.save(comanda);
    }


}
