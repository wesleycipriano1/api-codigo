package api_code.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api_code.dto.UsuarioRequestDTO;
import api_code.dto.UsuarioResponseDTO;
import api_code.entity.Usuario;
import api_code.exception.CamposVaziosException;
import api_code.exception.EmailCadastradoException;
import api_code.exception.UsuarioNaoEncontradoExeception;
import api_code.mapper.UsuarioMapper;
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
    @Autowired
    private UsuarioMapper usuarioMapper;

    public Optional<UsuarioResponseDTO> cadastrar(UsuarioRequestDTO usuarioRequestDTO) {
        if (usuarioRepository.findByEmail(usuarioRequestDTO.email()) != null) {
            throw new EmailCadastradoException("Email já cadastrado");
        }
        if (usuarioRequestDTO.senha() == null || usuarioRequestDTO.senha().isEmpty()) {
            throw new CamposVaziosException("Senha não pode ser nula ou vazia");

        }

        Usuario usuario = usuarioMapper.toEntity(usuarioRequestDTO);  
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return Optional.of(usuarioMapper.toDTO(usuarioSalvo));
    }

    public UsuarioResponseDTO atualizar(UsuarioRequestDTO usuarioAtualizado, String token) {
        Usuario usuario = obterUsuarioPeloToken(token);
        if (!usuario.getEmail().equals(usuarioAtualizado.email()) &&
                usuarioRepository.findByEmail(usuarioAtualizado.email()) != null) {
            throw new EmailCadastradoException("Email já cadastrado");
        }

        usuario.setNome(usuarioAtualizado.nome());
        usuario.setEmail(usuarioAtualizado.email());
        usuario.setEndereco(usuarioAtualizado.endereco());
        if (usuarioAtualizado.senha() != null && !usuarioAtualizado.senha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.senha()));
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuarioSalvo);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    protected Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void deletar(String token) {
        Usuario usuario = obterUsuarioPeloToken(token);
        usuarioRepository.deleteById(usuario.getId());
    }

    protected Usuario obterUsuarioPeloToken(String tokenCompleto) {
        String token = extrairToken(tokenCompleto);
        Long usuarioId = jwtService.obterId(token);
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoExeception("Usuário não encontrado"));
    }

    private String extrairToken(String header) {
        return header.replace("Bearer ", "").trim();
    }

    public UsuarioResponseDTO buscarUsuario(String token) {
        return usuarioMapper.toDTO(obterUsuarioPeloToken(token));

    }

    // usado somente para o admin,deve ser removido depois
    public void deletarADM(Long id) {
        usuarioRepository.deleteById(id);
    }
}
