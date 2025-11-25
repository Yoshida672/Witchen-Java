package br.com.fiap.gs_witchen_java.service;

import br.com.fiap.gs_witchen_java.entity.Comanda;
import br.com.fiap.gs_witchen_java.entity.ItemPedido;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import br.com.fiap.gs_witchen_java.entity.Pagamento;
import br.com.fiap.gs_witchen_java.entity.Pedido;
import br.com.fiap.gs_witchen_java.repository.ComandaRepository;
import br.com.fiap.gs_witchen_java.repository.PagamentoRepository;
import br.com.fiap.gs_witchen_java.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CozinhaService {

    private final BlockingQueue<ItemPedido> filaPedidos = new LinkedBlockingQueue<>();



    public void iniciarProcessamento() {
        new Thread(() -> {
            while (true) {
                try {
                    ItemPedido item = filaPedidos.take();
                    System.out.println("Walter preparando: " + item.getProdutoId());
                    Thread.sleep(2000);
                    System.out.println("Item pronto: " + item.getProdutoId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

