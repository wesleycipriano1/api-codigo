package api_code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api_code.service.RecuperacaoSenhaService;

import java.util.Map;

@RestController
@RequestMapping("/api/recuperar-senha")
public class RecuperarSenhaController {

    private final RecuperacaoSenhaService service;

    public RecuperarSenhaController(RecuperacaoSenhaService service) {
        this.service = service;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<Void> solicitar(@RequestBody Map<String, String> body) {
        service.solicitarRecuperacao(body.get("email"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/redefinir")
    public ResponseEntity<Void> redefinir(@RequestBody Map<String, String> body) {
        // Modifique para receber o token do corpo, não do header
        service.redefinirSenha(body.get("token"), body.get("novaSenha"));
        return ResponseEntity.ok().build();
    }
}
