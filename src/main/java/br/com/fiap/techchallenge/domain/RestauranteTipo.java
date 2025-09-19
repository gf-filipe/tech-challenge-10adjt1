package br.com.fiap.techchallenge.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "restaurante_tipo")
public class RestauranteTipo {
    @EmbeddedId
    private RestauranteTipoId id;

    @MapsId("idRestaurante")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_restaurante", nullable = false)
    private Restaurante idRestaurante;

    @MapsId("idTipoRestaurante")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_restaurante", nullable = false)
    private TipoRestaurante idTipoRestaurante;

}