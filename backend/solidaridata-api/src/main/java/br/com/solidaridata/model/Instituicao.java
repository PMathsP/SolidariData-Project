package br.com.solidaridata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "instituicao")
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instituicao")
    private Integer idInstituicao;

    @Column(name = "nome_instituicao", nullable = false, length = 200)
    private String nomeInstituicao;

    @Column(name = "razao_social", length = 200)
    private String razaoSocial;

    @Column(name = "cnpj", nullable = false, unique = true, columnDefinition = "char(14)")
    private String cnpj;

    @Column(name = "id_usuario_responsavel", nullable = false)
    private Integer idUsuarioResponsavel;

    @Column(name = "id_endereco")
    private Integer idEndereco;

    @Column(name = "id_telefone")
    private Integer idTelefone;

    @Column(name = "id_email")
    private Integer idEmail;
}