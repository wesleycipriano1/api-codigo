package api_code.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClasseRequestDTO {
    @NotBlank(message = "a linguagem não pode ser vazio")
    private String linguagem;
    @NotBlank(message = "O nome da classe não pode ser vazio")
    private String nomeClasse;
    @NotBlank(message = "O encapsulamento  não pode ser vazio")
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
