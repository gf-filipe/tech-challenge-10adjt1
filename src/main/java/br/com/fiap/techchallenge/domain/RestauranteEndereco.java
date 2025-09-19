package br.com.fiap.techchallenge.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "restaurante_endereco")
public class RestauranteEndereco {
    @EmbeddedId
    private RestauranteEnderecoId id;

    @MapsId("idRestaurante")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_restaurante", nullable = false)
    private Restaurante restaurante;

    @MapsId("idEndereco")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

}