package api_code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api_code.entity.Usuario;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario,Long>{

     Usuario findByEmail(String email);


    

    
}
