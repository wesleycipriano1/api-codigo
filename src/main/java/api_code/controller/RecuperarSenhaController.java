package api_code.controller;

import api_code.service.RecuperacaoSenhaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        service.redefinirSenha(body.get("token"), body.get("novaSenha"));
        return ResponseEntity.ok().build();
    }
}
