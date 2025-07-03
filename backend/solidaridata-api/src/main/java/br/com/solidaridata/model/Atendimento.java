package br.com.solidaridata.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "atendimento")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atendimento")
    private Integer idAtendimento;

    // A anotação @Column aqui é opcional se o nome do campo for igual ao da coluna,
    // mas é uma boa prática ser explícito.
    @Column(name = "data_atendimento", nullable = false)
    private LocalDateTime dataAtendimento;

    @Column(name = "nivel_urgencia", length = 50)
    private String nivelUrgencia;

    @Column(name = "id_instituicao", nullable = false)
    private Integer idInstituicao;

    @Column(name = "id_beneficiario", nullable = false)
    private Integer idBeneficiario;

    // Este método será útil para o banco de dados definir a data automaticamente ao criar
    @PrePersist
    public void prePersist() {
        if (dataAtendimento == null) {
            dataAtendimento = LocalDateTime.now();
        }
    }
}