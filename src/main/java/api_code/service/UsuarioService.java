package api_code.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api_code.entity.Usuario;
import api_code.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private  UsuarioRepository usuarioRepository;

@Autowired
private PasswordEncoder passwordEncoder;

public Usuario cadastrar(Usuario usuario) {
    String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
    usuario.setSenha(senhaCriptografada);
    return usuarioRepository.save(usuario);
}


    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
