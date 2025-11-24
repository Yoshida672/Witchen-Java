package br.com.fiap.gs_witchen_java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comanda_id")
    private Integer comandaId;

    @ManyToOne
    @JoinColumn(name = "comanda_id", insertable = false, updatable = false)
    private Comanda comanda;

    @Builder.Default
    private String status = "Em andamento";

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ItemPedido> itens = new ArrayList<>();

    // Método de negócio
    @Transient
    public double calcularTotal() {
        return itens.stream()
                .mapToDouble(ItemPedido::calcularSubtotal)
                .sum();
    }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}