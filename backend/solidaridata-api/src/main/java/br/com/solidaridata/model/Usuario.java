package br.com.solidaridata.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails { // Implementa a interface UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nome_usuario", nullable = false, length = 150)
    private String nomeUsuario;

    @Column(name = "cpf_usuario", nullable = false, unique = true, columnDefinition = "char(11)")
    private String cpfUsuario;

    @Column(name = "senha_hash", nullable = false, length = 255)
    private String senhaHash;

    @Column(name = "tipo_usuario", nullable = false)
    private Integer tipoUsuario;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "id_endereco")
    private Integer idEndereco;

    @Column(name = "id_telefone")
    private Integer idTelefone;

    @Column(name = "id_email")
    private Integer idEmail;


    // ==========================================================
    // MÉTODOS OBRIGATÓRIOS DA INTERFACE UserDetails
    // ==========================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Define o "papel" ou "permissão" do usuário.
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        // Informa ao Spring Security qual campo contém a senha.
        return this.senhaHash;
    }

    @Override
    public String getUsername() {
        // Informa ao Spring Security qual campo será usado como "login". Usaremos o CPF.
        return this.cpfUsuario;
    }

    // Os métodos abaixo controlam status da conta. Deixaremos como 'true' para simplificar.
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}