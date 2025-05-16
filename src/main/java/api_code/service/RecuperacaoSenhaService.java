package api_code.service;

import api_code.entity.RecuperacaoSenha;
import api_code.entity.Usuario;
import api_code.repository.RecuperacaoSenhaRepository;
import api_code.repository.UsuarioRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RecuperacaoSenhaService {

    private final UsuarioRepository usuarioRepository;
    private final RecuperacaoSenhaRepository recuperacaoSenhaRepository;
    private final RabbitTemplate rabbitTemplate;

    public RecuperacaoSenhaService(UsuarioRepository usuarioRepository,
                                   RecuperacaoSenhaRepository recuperacaoSenhaRepository,
                                   RabbitTemplate rabbitTemplate) {
        this.usuarioRepository = usuarioRepository;
        this.recuperacaoSenhaRepository = recuperacaoSenhaRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void solicitarRecuperacao(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now().plusHours(2);

        RecuperacaoSenha recuperacao = new RecuperacaoSenha();
        recuperacao.setToken(token);
        recuperacao.setExpiracao(expiracao);
        recuperacao.setUsuario(usuario);
        recuperacaoSenhaRepository.save(recuperacao);

        Map<String, String> payload = new HashMap<>();
        payload.put("email", usuario.getEmail());
        payload.put("token", token);
        payload.put("nome", usuario.getNome());

        rabbitTemplate.convertAndSend("", "email-recuperar", payload);
    }

    public void redefinirSenha(String token, String novaSenha) {
        RecuperacaoSenha recuperacao = recuperacaoSenhaRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (recuperacao.getExpiracao().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expirado");
        }

        Usuario usuario = recuperacao.getUsuario();
        usuario.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
        usuarioRepository.save(usuario);

        recuperacaoSenhaRepository.delete(recuperacao);
    }
}
