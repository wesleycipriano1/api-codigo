package api_code.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO(
                @NotBlank(message = "Nome é obrigatório") String nome,

                @NotBlank(message = "Email é obrigatório") @Email(message = "Email deve ser válido") String email,

                @NotBlank(message = "Senha é obrigatória") String senha,

                @NotBlank(message = "Telefone é obrigatório") String telefone,

                @NotBlank(message = "Endereço é obrigatório") String endereco) {
}
