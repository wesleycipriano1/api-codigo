package api_code.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "Email é obrigatório")
    String email, 
    @NotBlank(message = "Senha é obrigatória")
    String senha) {
}
