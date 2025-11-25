package br.com.fiap.gs_witchen_java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Comandas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdComanda")
    private Integer idComanda;

    @Column(name = "MesaId")
    private Integer mesaId;

    @ManyToOne
    @JoinColumn(name = "MesaId", insertable = false, updatable = false)
    private Mesa mesa;

    @Column(name = "DataAbertura")
    private LocalDateTime dataAbertura;

    @Column(name = "DataFechamento")
    private LocalDateTime dataFechamento;

    @Column(name = "Status")
    private String status;

    @Builder.Default
    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Pedido> pedidos = new ArrayList<>();

    @OneToOne(mappedBy = "comanda", cascade = CascadeType.ALL)
    private Pagamento pagamento;
  // Métodos de negócio
    @Transient
    public double calcularTotal() {
        return pedidos.stream()
                .filter(p -> !"Cancelado".equals(p.getStatus()))
                .mapToDouble(Pedido::calcularTotal)
                .sum();
    }
    public void setStatus(String status) {
        this.status = status == null ? null : status.toUpperCase();
    }
    public void fecharComanda(String metodoPagamento) {
        if (!"Aberta".equals(this.status)) {
            throw new IllegalStateException("Comanda já está fechada.");
        }

        double total = calcularTotal();
        this.pagamento = new Pagamento(this.idComanda, total, metodoPagamento);
        this.status = "Fechada";
        this.dataFechamento = LocalDateTime.now();

        if (mesa != null) {
            mesa.setStatus("Livre");
        }
    }
    @PrePersist
    public void prePersist() {
        if (dataAbertura == null) {
            dataAbertura = LocalDateTime.now();
        }
        if (status == null) {
            status = "Aberta";
        }
    }

}