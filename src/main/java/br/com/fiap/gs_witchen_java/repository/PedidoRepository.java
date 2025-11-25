package br.com.fiap.gs_witchen_java.repository;

import br.com.fiap.gs_witchen_java.entity.Comanda;
import br.com.fiap.gs_witchen_java.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByStatusIgnoreCase(String status);
    List<Pedido> findByComandaId(Integer comandaId);


}