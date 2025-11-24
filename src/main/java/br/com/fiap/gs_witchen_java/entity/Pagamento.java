package br.com.fiap.gs_witchen_java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagamento")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPagamento;

    @Column(name = "comanda_id")
    private Integer comandaId;

    @OneToOne
    @JoinColumn(name = "comanda_id", insertable = false, updatable = false)
    private Comanda comanda;

    private double valorTotal;

    @Builder.Default
    private LocalDateTime dataPagamento = LocalDateTime.now();

    private String metodo;

    @Builder.Default
    private String status = "Pendente";

    public Pagamento(Integer comandaId, double valorTotal, String metodo) {
        if (valorTotal <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser maior que zero.");
        }
        if (metodo == null || metodo.isBlank()) {
            throw new IllegalArgumentException("Método de pagamento inválido.");
        }

        this.comandaId = comandaId;
        this.valorTotal = valorTotal;
        this.metodo = metodo;
        this.status = "Concluído";
        this.dataPagamento = LocalDateTime.now();
    }

    public void cancelar() {
        this.status = "Cancelado";
    }
}