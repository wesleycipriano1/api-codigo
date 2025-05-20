package api_code.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api_code.entity.Usuario;
import api_code.exception.CredenciaisInvalidasException;
import api_code.repository.UsuarioRepository;
import api_code.security.dto.RequisicaoDTO;
import api_code.security.jwt.JwtService;

@Service
public class ServicoAutenticacao {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public ServicoAutenticacao(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String autenticar(RequisicaoDTO requisicao) {
        Usuario usuario = usuarioRepository.findByEmail(requisicao.getEmail());
        if (usuario != null && passwordEncoder.matches(requisicao.getSenha(), usuario.getSenha())) {
            return jwtService.gerarToken(usuario.getEmail(), usuario.getId());
        }
        throw new CredenciaisInvalidasException("Credenciais inválidas");
    }
}
