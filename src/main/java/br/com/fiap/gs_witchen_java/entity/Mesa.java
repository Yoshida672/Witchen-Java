package br.com.fiap.gs_witchen_java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mesa")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMesa;

    private Integer numero;

    @Builder.Default
    private String status = "Livre";

    @Column(name = "restaurante_id")
    private Integer restauranteId;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", insertable = false, updatable = false)
    private Restaurante restaurante;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comanda> comandas = new ArrayList<>();

    @Transient
    public Comanda getComandaAtiva() {
        return comandas.stream()
                .filter(c -> "Aberta".equals(c.getStatus()))
                .findFirst()
                .orElse(null);
    }
    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }
}