package br.com.fiap.gs_witchen_java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Mesas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdMesa")

    private Integer idMesa;

    private Integer numero;

    @Builder.Default
    private String status = "Livre";

    @Column(name = "RestauranteId")
    private Integer restauranteId;

    @ManyToOne
    @JoinColumn(name = "RestauranteId", insertable = false, updatable = false)
    private Restaurante restaurante;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comanda> comandas = new ArrayList<>();

    public void setStatus(String status) {
        this.status = status == null ? null : status.toUpperCase();
    }
    @Transient
    public Comanda getComandaAtiva() {
        return comandas.stream()
                .filter(c -> "Aberta".equals(c.getStatus()))
                .findFirst()
                .orElse(null);
    }

}