package br.com.fiap.gs_witchen_java.service;

import br.com.fiap.gs_witchen_java.dto.ProdutoDTO;
import br.com.fiap.gs_witchen_java.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoDTO> getTodosProdutos() {
        return produtoRepository.findAll().stream()
                .map(p -> new ProdutoDTO(p.getIdProduto(), p.getNomeProduto(),p.getIngredientesProduto(),p.getPrecoProduto()))
                .collect(Collectors.toList());
    }
}