package api_code.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api_code.entity.Usuario;
import api_code.service.UsuarioService;
import jakarta.validation.Valid;

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
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody @Valid Usuario usuario) {
        return ResponseEntity.ok(usuarioService.cadastrar(usuario));
    }

    @PutMapping()
    public ResponseEntity<Usuario> atualizarUsuarioLogado(@RequestHeader("Authorization") String token,
            @RequestBody @Valid Usuario usuarioAtualizado) {

        return ResponseEntity.ok(usuarioService.atualizar(usuarioAtualizado, token));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deletarUsuarioLogado(@RequestHeader("Authorization") String token) {
        usuarioService.deletar(token);
        return ResponseEntity.noContent().build();
    }
}