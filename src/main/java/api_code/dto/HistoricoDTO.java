package api_code.dto;

import java.time.LocalDateTime;

import api_code.entity.HistoricoClasse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoricoDTO {
    private String linguagem;
    private String codigo;
    private LocalDateTime dataModificacao;
    private String emailDoUsuario;

    public HistoricoDTO(HistoricoClasse historico) {
        this.linguagem = historico.getLinguagem();
        this.codigo = historico.getCodigo();
        this.dataModificacao = historico.getDataModificacao();
        this.emailDoUsuario = historico.getUsuario().getEmail();
    }

}
