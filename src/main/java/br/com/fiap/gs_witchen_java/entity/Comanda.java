package br.com.fiap.gs_witchen_java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comanda")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComanda;

    @Column(name = "mesa_id")
    private Integer mesaId;

    @ManyToOne
    @JoinColumn(name = "mesa_id", insertable = false, updatable = false)
    private Mesa mesa;

    @Builder.Default
    private LocalDateTime dataAbertura = LocalDateTime.now();

    private LocalDateTime dataFechamento;

    @Builder.Default
    private String status = "Aberta";

    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
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
    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }
}