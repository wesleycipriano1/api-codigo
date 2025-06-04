package api_code.dto;

import api_code.entity.HistoricoClasse;

public record HistoricoDTO(
        Long id,
        String linguagem,
        String codigo,
        String emailDoUsuario) {

    public HistoricoDTO(HistoricoClasse historico) {
        this(
                historico.getId(),
                historico.getLinguagem(),
                historico.getCodigo(),
                historico.getUsuario().getEmail());
    }
}
