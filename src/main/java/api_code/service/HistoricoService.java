package api_code.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @CacheEvict(value = "historico", key = "#tokenCompleto")
    public String gerarClasseComHistorico(ClasseRequestDTO dto, String tokenCompleto) {
        Usuario usuario = usuarioService.obterUsuarioPeloToken(tokenCompleto);
        String codigoGerado = ClasseFactory.gerar(dto);
        salvarHistorico(dto.getLinguagem(), codigoGerado, usuario);
        return codigoGerado;
    }

    @Cacheable(value = "historico", key = "#tokenCompleto")
    public List<HistoricoDTO> buscarHistoricoDoUsuario(String tokenCompleto) {
        System.out.println("deu redis aqui");
        Usuario usuario = usuarioService.obterUsuarioPeloToken(tokenCompleto);

        return historicoRepo.findByUsuarioId(usuario.getId())
                .stream()
                .map(HistoricoDTO::new)
                .collect(Collectors.toList());
    }

    private void salvarHistorico(String linguagem, String codigo, Usuario usuario) {
        HistoricoClasse historico = new HistoricoClasse();
        historico.setLinguagem(linguagem);
        historico.setCodigo(codigo);
        historico.setDataModificacao(LocalDateTime.now());
        historico.setUsuario(usuario);

        historicoRepo.save(historico);
    }

    @CacheEvict(value = "historico", key = "#tokenCompleto")
    public Void deletarHistorico(String tokenCompleto, Long id) {
        Usuario usuario = usuarioService.obterUsuarioPeloToken(tokenCompleto);
        if (usuario != null && historicoRepo.findById(id).isPresent()) {
            historicoRepo.deleteById(id);
            return null;
        } else {
            throw new UsuarioNaoEncontradoExeception("Usuário não encontrado ou histórico não encontrado");
        }
    }

}
