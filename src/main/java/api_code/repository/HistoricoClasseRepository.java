package api_code.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import api_code.dto.HistoricoDTO;
import api_code.entity.HistoricoClasse;

public interface HistoricoClasseRepository extends JpaRepository<HistoricoClasse, Long> {
    
    List<HistoricoClasse> findByUsuarioId(Long usuarioId);
    
    @Query("""
           SELECT new api_code.dto.HistoricoDTO(
               h.id,
               h.linguagem,
               h.codigo,
               u.email
           )
           FROM HistoricoClasse h
           JOIN h.usuario u
           WHERE h.usuario.id = :usuarioId
           """)
    List<HistoricoDTO> findHistoricoDTOByUsuarioId(@Param("usuarioId") Long usuarioId);
}
