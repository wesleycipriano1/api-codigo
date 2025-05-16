package api_code.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api_code.entity.Usuario;
import api_code.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private final UsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity<Usuario> buscarUsuarioLogado(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.obterUsuarioPeloToken(token));
    }

    @PostMapping()
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.cadastrar(usuario));
    }

    @PutMapping()
    public ResponseEntity<Usuario> atualizarUsuarioLogado(@RequestHeader("Authorization") String token,
            @RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = usuarioService.obterUsuarioPeloToken(token);
        usuarioAtualizado.setId(usuario.getId());
        return ResponseEntity.ok(usuarioService.cadastrar(usuarioAtualizado));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deletarUsuarioLogado(@RequestHeader("Authorization") String token) {
        Usuario usuario = usuarioService.obterUsuarioPeloToken(token);
        usuarioService.deletar(usuario.getId());
        return ResponseEntity.noContent().build();
    }
}