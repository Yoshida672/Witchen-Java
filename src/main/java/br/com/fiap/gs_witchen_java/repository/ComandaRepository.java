package br.com.fiap.gs_witchen_java.repository;

import br.com.fiap.gs_witchen_java.entity.Comanda;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Integer> {
    List<Comanda> findByMesaIdAndStatusIgnoreCase(Integer mesaId, String status);
    List<Comanda> findByStatusIgnoreCase(String status);
    Optional<Comanda> findById(Integer id);

}