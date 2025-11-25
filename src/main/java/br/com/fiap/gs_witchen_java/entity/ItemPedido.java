package br.com.fiap.gs_witchen_java.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ProdPedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdItemPedido")
    private Integer idItemPedido;

    @Column(name = "ComandaId")
    private Integer comandaId;

    @Column(name = "PedidoId")
    private Integer pedidoId;

    @ManyToOne
    @JoinColumn(name = "PedidoId", insertable = false, updatable = false)
    private Pedido pedido;

    @Column(name = "ProdutoId")
    private Integer produtoId;

    @ManyToOne
    @JoinColumn(name = "ProdutoId", insertable = false, updatable = false)
    private Produto produto;

    @Column(name = "Quantidade")
    private Integer quantidade;

    @Transient
    public double calcularSubtotal() {
        return quantidade * produto.getPrecoProduto();
    }

}
