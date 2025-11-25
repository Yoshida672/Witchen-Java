package br.com.fiap.gs_witchen_java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")

    private Integer id;

    @Column(name = "ComandaId")
    private Integer comandaId;

    @ManyToOne
    @JoinColumn(name = "ComandaId", insertable = false, updatable = false)
    private Comanda comanda;

    @Builder.Default
    private String status = "Em ANDAMENTO";

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<ItemPedido> itens = new ArrayList<>();

    public void setStatus(String status) {
        this.status = status == null ? null : status.toUpperCase();
    }    @Transient
    public double calcularTotal() {
        return itens.stream()
                .mapToDouble(ItemPedido::calcularSubtotal)
                .sum();
    }


}