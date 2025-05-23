package api_code.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api_code.dto.ClasseRequestDTO;
import api_code.dto.HistoricoDTO;
import api_code.entity.HistoricoClasse;
import api_code.entity.Usuario;
import api_code.exception.UsuarioNaoEncontradoExeception;
import api_code.repository.HistoricoClasseRepository;
import api_code.util.ClasseFactory;

@Service
public class HistoricoService {

    @Autowired
    private HistoricoClasseRepository historicoRepo;

    @Autowired
    private UsuarioService usuarioService;

    public String gerarClasseComHistorico(ClasseRequestDTO dto, String tokenCompleto) {
        String codigoGerado = ClasseFactory.gerar(dto);
        salvarHistorico(dto.getLinguagem(), codigoGerado, tokenCompleto);
        return codigoGerado;
    }

    public List<HistoricoDTO> buscarHistoricoDoUsuario(String tokenCompleto) {
        Usuario usuario = usuarioService.obterUsuarioPeloToken(tokenCompleto);

        return historicoRepo.findByUsuarioId(usuario.getId())
                .stream()
                .map(HistoricoDTO::new)
                .collect(Collectors.toList());
    }

    private void salvarHistorico(String linguagem, String codigo, String tokenCompleto) {
        Usuario usuario = usuarioService.obterUsuarioPeloToken(tokenCompleto);

        HistoricoClasse historico = new HistoricoClasse();
        historico.setLinguagem(linguagem);
        historico.setCodigo(codigo);
        historico.setDataModificacao(LocalDateTime.now());
        historico.setUsuario(usuario);

        historicoRepo.save(historico);
    }

    public Void deletarHistorico(String token, Long id) {
        System.out.println("Deletando historico com id: " + id);
        Usuario usuario = usuarioService.obterUsuarioPeloToken(token);
        if (usuario != null && historicoRepo.findById(id).isPresent()) {
            historicoRepo.deleteById(id);
            return null;

        } else {
            throw new UsuarioNaoEncontradoExeception("Usuário não encontrado ou histórico não encontrado");
            

        }

    }

}
