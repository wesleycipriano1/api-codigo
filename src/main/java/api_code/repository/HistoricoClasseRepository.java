package api_code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import api_code.entity.HistoricoClasse;

public interface HistoricoClasseRepository extends JpaRepository<HistoricoClasse, Long> {
    List<HistoricoClasse> findByUsuarioId(Long usuarioId);
}
