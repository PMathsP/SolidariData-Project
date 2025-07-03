package br.com.solidaridata.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "beneficiario")
public class Beneficiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_beneficiario")
    private Integer idBeneficiario;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "cpf", unique = true, columnDefinition = "char(11)")
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "grau_parentesco", length = 50)
    private String grauParentesco;

    @Column(name = "id_beneficiario_responsavel")
    private Integer idBeneficiarioResponsavel;

    @Column(name = "id_endereco")
    private Integer idEndereco;

    @Column(name = "id_telefone")
    private Integer idTelefone;

    @Column(name = "id_email")
    private Integer idEmail;
}