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
public class PedidoItemId implements Serializable {
    private static final long serialVersionUID = -6069851751155096656L;
    @NotNull
    @Column(name = "id_pedido", nullable = false)
    private Long idPedido;

    @NotNull
    @Column(name = "id_cardapio", nullable = false)
    private Long idCardapio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PedidoItemId entity = (PedidoItemId) o;
        return Objects.equals(this.idPedido, entity.idPedido) &&
                Objects.equals(this.idCardapio, entity.idCardapio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, idCardapio);
    }

}