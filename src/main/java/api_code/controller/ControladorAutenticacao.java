package api_code.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api_code.entity.Usuario;
import api_code.security.RequisicaoDTO;
import api_code.security.RespostaDTO;
import api_code.security.ServicoAutenticacao;
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
    @ResponseStatus(HttpStatus.OK)
    public RespostaDTO login(@RequestBody RequisicaoDTO requisicao) {
        String token = servicoAutenticacao.autenticar(requisicao);
        return new RespostaDTO(token);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.cadastrar(usuario));
    }
    

}
