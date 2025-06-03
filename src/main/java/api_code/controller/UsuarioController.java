package api_code.controller;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import api_code.dto.UsuarioRequestDTO;
import api_code.dto.UsuarioResponseDTO;
import api_code.entity.Usuario;
import api_code.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
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

    @PutMapping
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuarioLogado(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {

        UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizar(usuarioRequestDTO, token);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deletarUsuarioLogado(@RequestHeader("Authorization") String token) {
        usuarioService.deletar(token);
        return ResponseEntity.noContent().build();
    }
}