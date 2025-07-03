package br.com.solidaridata.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servico_instituicao")
public class ServicoInstituicao {

    @EmbeddedId // Marca que a chave primária é uma classe embutida
    private ServicoInstituicaoId id;
}