package api_code.dto;

import java.time.LocalDateTime;
import api_code.entity.HistoricoClasse;

public record HistoricoDTO(
        Long id,
        String linguagem,
        String codigo,
        LocalDateTime dataModificacao,
        String emailDoUsuario) {
   
    public HistoricoDTO(HistoricoClasse historico) {
        this(
                historico.getId(),
                historico.getLinguagem(),
                historico.getCodigo(),
                historico.getDataModificacao(),
                historico.getUsuario().getEmail());
    }
}
