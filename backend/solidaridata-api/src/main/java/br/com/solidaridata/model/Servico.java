package br.com.solidaridata.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servico")
    private Integer idServico;

    @Column(name = "descricao_servico", nullable = false, length = 200)
    private String descricaoServico;
}