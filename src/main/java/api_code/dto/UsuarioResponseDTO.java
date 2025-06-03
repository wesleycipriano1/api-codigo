package api_code.dto;

import api_code.entity.Usuario;

public record UsuarioResponseDTO(Long id,String nome, String email, String telefone) {
public UsuarioResponseDTO(Usuario usuario) {
       this(usuario.getId() ,usuario.getNome(), usuario.getEmail(), usuario.getTelefone());
}
}