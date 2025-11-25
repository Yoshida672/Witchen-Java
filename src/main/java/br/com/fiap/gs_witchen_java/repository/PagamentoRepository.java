package br.com.fiap.gs_witchen_java.repository;

import br.com.fiap.gs_witchen_java.entity.Pagamento;
import br.com.fiap.gs_witchen_java.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
