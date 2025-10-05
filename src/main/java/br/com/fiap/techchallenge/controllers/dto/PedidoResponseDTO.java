package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.Pedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PedidoResponseDTO {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataPedido;
    private String status;

    public PedidoResponseDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.dataPedido = pedido.getDataPedido();
        if (pedido.getStatusPedido() != null) {
            this.status = pedido.getStatusPedido().getStatus();
        }
    }
}
