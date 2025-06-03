package api_code.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api_code.dto.ClasseRequestDTO;
import api_code.dto.HistoricoDTO;
import api_code.service.HistoricoService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/classe")
public class ClasseController {

    @Autowired
    private HistoricoService historicoService;

    @PostMapping("/gerar")
    public ResponseEntity<String> gerarClasse(@RequestBody ClasseRequestDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(HttpStatus.OK).body(historicoService.gerarClasseComHistorico(dto, token));
    }

    @GetMapping("/historico")
    public ResponseEntity<List<HistoricoDTO>> historico(@RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(historicoService.buscarHistoricoDoUsuario(token));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarHistorico(@RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        historicoService.deletarHistorico(token, id);
        return ResponseEntity.noContent().build();
    }

}
