package api_code.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api_code.entity.Usuario;
import api_code.repository.UsuarioRepository;
import api_code.security.JwtServico;
import api_code.security.RequisicaoDTO;

@Service
public class ServicoAutenticacao {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServico jwtServico;

    public ServicoAutenticacao(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtServico jwtServico) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtServico = jwtServico;
    }

    public String autenticar(RequisicaoDTO requisicao) {
        Usuario usuario = usuarioRepository.findByEmail(requisicao.getEmail());
        if (usuario != null && passwordEncoder.matches(requisicao.getSenha(), usuario.getSenha())) {
            return jwtServico.gerarToken(usuario.getEmail());
        }
        throw new RuntimeException("Credenciais inválidas");
    }
}

