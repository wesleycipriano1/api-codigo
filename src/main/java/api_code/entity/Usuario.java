package api_code.entity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode estar vazio.")
    private String nome;

    @Column(unique = true)
    @NotBlank(message = "Email não pode estar vazio.")
    @Email(message = "Email deve ser válido.")
    private String email;

    @NotBlank(message = "Senha não pode estar vazio.")
    private String senha;

    @NotBlank
    private String telefone;

    @NotBlank(message = "Endereço não pode estar vazio.")
    private String endereco;

    @Column(columnDefinition = "TEXT")
    private String historico;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoClasse> historicosAssociados;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecuperacaoSenha> recuperacoes;

    public UserDetails toUserDetails() {
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(email, senha, authorities);
    }
}
