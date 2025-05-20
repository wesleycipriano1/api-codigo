package api_code.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api_code.entity.Usuario;
import api_code.exception.EmailCadastradoException;
import api_code.exception.UsuarioNãoEncontradoExeception;
import api_code.repository.UsuarioRepository;
import api_code.security.jwt.JwtService;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario cadastrar(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()) == null) {

            String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);
            return usuarioRepository.save(usuario);
        }
        throw new EmailCadastradoException("Email já cadastrado");

    }

    public Usuario atualizar(Usuario usuarioAtualizado, String token) {
        Usuario usuarioOld = obterUsuarioPeloToken(token);

        if (usuarioOld == null) {
            throw new UsuarioNãoEncontradoExeception("Usuário não encontrado");
        }

        if (buscarPorId(usuarioOld.getId()).isPresent()) {
            usuarioAtualizado.setId(usuarioOld.getId());
            usuarioAtualizado.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
            return usuarioRepository.save(usuarioAtualizado);
        }

        throw new UsuarioNãoEncontradoExeception("Usuário não encontrado");
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void deletar(String token) {
        Usuario usuario = obterUsuarioPeloToken(token);
        usuarioRepository.deleteById(usuario.getId());
    }

    public Usuario obterUsuarioPeloToken(String tokenCompleto) {
        String token = extrairToken(tokenCompleto);
        Long usuarioId = jwtService.obterId(token);
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNãoEncontradoExeception("Usuário não encontrado"));
    }

    private String extrairToken(String header) {
        return header.replace("Bearer ", "").trim();
    }


    //usado somente para o admin,deve ser removido depois
    public void deletarADM(Long id) {
        usuarioRepository.deleteById(id);
    }
}
