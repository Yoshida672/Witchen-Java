package br.com.fiap.gs_witchen_java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurante")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRestaurante;

    @Column(nullable = false)
    private String nomeRest;

    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 16)
    private String senha;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Mesa> mesas = new ArrayList<>();

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Produto> produtos = new ArrayList<>();

    // Métodos de negócio
    public void atualizarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do restaurante não pode ser vazio.");
        }
        this.nomeRest = nome;
    }

    public void atualizarCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("O CNPJ não pode ser vazio.");
        }
        if (cnpj.length() != 18) {
            throw new IllegalArgumentException("O CNPJ deve seguir o formato 99.999.999/9999-99.");
        }
        this.cnpj = cnpj;
    }

    public void atualizarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O e-mail não pode ser vazio.");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        this.email = email;
    }

    public void atualizarSenha(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha não pode ser vazia.");
        }
        if (senha.length() > 16) {
            throw new IllegalArgumentException("A senha deve ter no máximo 16 caracteres.");
        }
        this.senha = senha;
    }

    public void adicionarMesa(Mesa mesa) {
        if (mesa == null) {
            throw new IllegalArgumentException("Mesa inválida.");
        }
        mesas.add(mesa);
    }

    public void adicionarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto inválido.");
        }
        produtos.add(produto);
    }
}