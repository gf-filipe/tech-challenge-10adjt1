package br.com.fiap.techchallenge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class RestauranteTipoId implements Serializable {
    private static final long serialVersionUID = 7657282789325131373L;
    @NotNull
    @Column(name = "id_restaurante", nullable = false)
    private Long idRestaurante;

    @NotNull
    @Column(name = "id_tipo_restaurante", nullable = false)
    private Integer idTipoRestaurante;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RestauranteTipoId entity = (RestauranteTipoId) o;
        return Objects.equals(this.idRestaurante, entity.idRestaurante) &&
                Objects.equals(this.idTipoRestaurante, entity.idTipoRestaurante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRestaurante, idTipoRestaurante);
    }

}