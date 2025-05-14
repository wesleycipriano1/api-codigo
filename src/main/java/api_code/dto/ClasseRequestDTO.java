package api_code.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClasseRequestDTO {

    private String linguagem;
    private String nomeClasse;
    private String encapsulamentoClasse;
    private String heranca;

    private List<AtributoDTO> atributos;
    @Getter
    @Setter
    public static class AtributoDTO {
        private String nome;
        private String tipo;
        private String encapsulamento;

    }
}
