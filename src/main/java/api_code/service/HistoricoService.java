package api_code.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import api_code.dto.HistoricoDTO;
import api_code.entity.HistoricoClasse;
import api_code.entity.Usuario;
import api_code.repository.HistoricoClasseRepository;
import api_code.repository.UsuarioRepository;
import api_code.security.JwtService;


@Service
public class HistoricoService {

    @Autowired
    private HistoricoClasseRepository historicoRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void salvarHistorico(String linguagem, String codigo, String tokenJwt) {
       System.out.println("o token esta vindo como-------->"+tokenJwt); 
    Long usuarioId = jwtService.obterId(tokenJwt); 
        System.out.println("o aida esta vindo como-------->"+usuarioId);
    Usuario usuario = usuarioRepository.findById(usuarioId)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

    HistoricoClasse historico = new HistoricoClasse();
    historico.setLinguagem(linguagem);
    historico.setCodigo(codigo);
    historico.setDataModificacao(LocalDateTime.now());
    historico.setUsuario(usuario);

    historicoRepo.save(historico);
}

public List<HistoricoDTO> listarHistoricoDoUsuario(String tokenJwt) {
    Long usuarioId = jwtService.obterId(tokenJwt);
    return historicoRepo.findByUsuarioId(usuarioId)
        .stream()
        .map(HistoricoDTO::new)
        .collect(Collectors.toList());
}


}

