package api_code.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api_code.dto.ClasseRequestDTO;
import api_code.dto.HistoricoDTO;
import api_code.service.HistoricoService;

@RestController
@RequestMapping("/classe")
public class ClasseController {

    @Autowired
    private HistoricoService historicoService;

    @PostMapping("/gerar")
    public ResponseEntity<String> gerarClasse(@RequestBody ClasseRequestDTO dto,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(historicoService.gerarClasseComHistorico(dto, token));
    }

    @GetMapping("/historico")
    public ResponseEntity<List<HistoricoDTO>> historico(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(historicoService.buscarHistoricoDoUsuario(token));
    }

}
