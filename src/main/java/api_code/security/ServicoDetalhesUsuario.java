package api_code.security;



import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import api_code.entity.Usuario;
import api_code.repository.UsuarioRepository;

@Service
public class ServicoDetalhesUsuario implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public ServicoDetalhesUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        return usuario.toUserDetails();
    }
}
