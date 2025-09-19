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
public class RestauranteEnderecoId implements Serializable {
    private static final long serialVersionUID = 4169540088407558020L;
    @NotNull
    @Column(name = "id_restaurante", nullable = false)
    private Long idRestaurante;

    @NotNull
    @Column(name = "id_endereco", nullable = false)
    private Long idEndereco;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RestauranteEnderecoId entity = (RestauranteEnderecoId) o;
        return Objects.equals(this.idRestaurante, entity.idRestaurante) &&
                Objects.equals(this.idEndereco, entity.idEndereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRestaurante, idEndereco);
    }

}