package api_code.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api_code.entity.RecuperacaoSenha;

public interface RecuperacaoSenhaRepository extends JpaRepository<RecuperacaoSenha,Long>{
    Optional<RecuperacaoSenha> findByToken(String token);

    

    
}
