package api_code.dto;

public record UsuarioRequestDTO(String nome,
        String email,
        String senha,
        String telefone,
        String endereco) {

}
