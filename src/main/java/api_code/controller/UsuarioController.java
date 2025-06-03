package api_code.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api_code.dto.UsuarioRequestDTO;
import api_code.dto.UsuarioResponseDTO;
import api_code.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Usuarios ", description = "Operações relacionadas a usuarios")
@RequestMapping("/api/usuarios")

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioLogado(
            @RequestHeader("Authorization") String token) {
        UsuarioResponseDTO usuario = usuarioService.buscarUsuario(token);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuarioLogado(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {

        UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizar(usuarioRequestDTO, token);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deletarUsuarioLogado(@RequestHeader("Authorization") String token) {
        usuarioService.deletar(token);
        return ResponseEntity.noContent().build();
    }
}