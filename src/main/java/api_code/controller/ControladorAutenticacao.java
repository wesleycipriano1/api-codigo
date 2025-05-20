package api_code.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api_code.entity.Usuario;
import api_code.security.dto.RequisicaoDTO;
import api_code.security.dto.RespostaDTO;
import api_code.security.service.ServicoAutenticacao;
import api_code.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class ControladorAutenticacao {

    private final ServicoAutenticacao servicoAutenticacao;
    private final UsuarioService usuarioService;

    public ControladorAutenticacao(ServicoAutenticacao servicoAutenticacao, UsuarioService usuarioService) {
        this.servicoAutenticacao = servicoAutenticacao;
        this.usuarioService = usuarioService;

    }

    @PostMapping("/login")
    public ResponseEntity<RespostaDTO> login(@RequestBody RequisicaoDTO requisicao) {
        String token = servicoAutenticacao.autenticar(requisicao);
        return ResponseEntity.ok(new RespostaDTO(token));

    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.cadastrar(usuario));
    }
    //usado para remover um usuario sem a necessidade de token,somente para o admin,deve ser excluido depois,usado pra testes.
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarUsuarioLogado(@PathVariable Long id) {
        usuarioService.deletarADM(id);
        return ResponseEntity.noContent().build();
    }

}
