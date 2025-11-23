package br.com.fiap.gs_witchen_java.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItemPedido;

    @Column(name = "comanda_id")
    private Integer comandaId;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column(name = "pedido_id", insertable = false, updatable = false)
    private Integer pedidoId;

    @Column(name = "produto_id")
    private Integer produtoId;

    @ManyToOne
    @JoinColumn(name = "produto_id", insertable = false, updatable = false)
    private Produto produto;

    private Integer quantidade;

    @Transient
    public double calcularSubtotal() {
        return quantidade * produto.getPrecoProduto();
    }
}