package api_code.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import api_code.dto.UsuarioRequestDTO;
import api_code.dto.UsuarioResponseDTO;
import api_code.security.dto.RequisicaoDTO;
import api_code.security.dto.RespostaDTO;
import api_code.security.service.ServicoAutenticacao;
import api_code.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class ControladorAutenticacao {
    @Autowired
    private ServicoAutenticacao servicoAutenticacao;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<RespostaDTO> login(@Valid @RequestBody RequisicaoDTO requisicao) {
        String token = servicoAutenticacao.autenticar(requisicao);
        return ResponseEntity.ok(new RespostaDTO(token));

    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(
            @RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO novoUsuarioResponseDTO = usuarioService.cadastrar(usuarioRequestDTO)
                .orElseThrow(() -> new RuntimeException("Erro ao cadastrar usuário"));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoUsuarioResponseDTO.id())
                .toUri();
        return ResponseEntity.created(location).body(novoUsuarioResponseDTO);
    }

    // usado para remover um usuario sem a necessidade de token,somente para o
    // admin,deve ser excluido depois,usado pra testes.
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarUsuarioLogado(@PathVariable Long id) {
        usuarioService.deletarADM(id);
        return ResponseEntity.noContent().build();
    }

}
