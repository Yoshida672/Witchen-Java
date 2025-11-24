package br.com.fiap.gs_witchen_java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produto")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduto;

    @Column(nullable = false)
    private String nomeProduto;

    @Column(nullable = false)
    private double precoProduto;

    @Column(nullable = false)
    private String ingredientesProduto;

    @Column(name = "restaurante_id")
    private Integer restauranteId;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", insertable = false, updatable = false)
    private Restaurante restaurante;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ItemPedido> itensPedidos = new ArrayList<>();

    // Métodos de negócio
    public void atualizarNome(String novoNome) {
        if (novoNome == null || novoNome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto inválido.");
        }
        this.nomeProduto = novoNome;
    }

    public void atualizarPreco(double novoPreco) {
        if (novoPreco <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero.");
        }
        this.precoProduto = novoPreco;
    }

    public void atualizarIngredientes(String novosIngredientes) {
        if (novosIngredientes == null || novosIngredientes.isBlank()) {
            throw new IllegalArgumentException("Ingredientes inválidos.");
        }
        this.ingredientesProduto = novosIngredientes;
    }
    public double getPrecoProduto() { return precoProduto; }
    public void setPrecoProduto(double precoProduto) { this.precoProduto = precoProduto; }
}