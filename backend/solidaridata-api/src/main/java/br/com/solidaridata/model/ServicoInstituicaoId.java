package br.com.solidaridata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable // Anotação que diz ao JPA que esta classe pode ser embutida em outra entidade
public class ServicoInstituicaoId implements Serializable {

    @Column(name = "id_instituicao")
    private Integer idInstituicao;

    @Column(name = "id_servico")
    private Integer idServico;
}