package br.com.fiap.gs_witchen_java.repository;

import br.com.fiap.gs_witchen_java.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {
}