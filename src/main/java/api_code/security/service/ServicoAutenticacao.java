package api_code.security.service;

import java.util.concurrent.TimeUnit;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api_code.entity.Usuario;
import api_code.exception.BloqueadoException;
import api_code.exception.CredenciaisInvalidasException;
import api_code.repository.UsuarioRepository;
import api_code.security.dto.RequisicaoDTO;
import api_code.security.jwt.JwtService;

@Service
public class ServicoAutenticacao {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;

    public ServicoAutenticacao(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
            JwtService jwtService, LoginAttemptService loginAttemptService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.loginAttemptService = loginAttemptService;
    }

    public String autenticar(RequisicaoDTO requisicao) {
        String email = requisicao.email();

        if (loginAttemptService.isBlocked(email)) {
            long remainingSeconds = loginAttemptService.getRemainingBlockTime(email, TimeUnit.MINUTES);
            throw new BloqueadoException("Usuário bloqueado por muitas tentativas de login, tentte novamente em: " + remainingSeconds + "minutos", remainingSeconds);

        }
        Usuario usuario = usuarioRepository.findByEmail(requisicao.email());
        if (usuario != null && passwordEncoder.matches(requisicao.senha(), usuario.getSenha())) {
            loginAttemptService.loginSucceeded(usuario.getEmail());
            return jwtService.gerarToken(usuario.getEmail(), usuario.getId());

        }
        loginAttemptService.loginFailed(email);
        throw new CredenciaisInvalidasException("Credenciais inválidas");
    }
}
