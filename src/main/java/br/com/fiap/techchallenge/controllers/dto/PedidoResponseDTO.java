package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.Pedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Schema(description = "DTO para resposta de pedido")
public class PedidoResponseDTO {
    @Schema(description = "ID do pedido", example = "1")
    private Long id;

    @Schema(description = "Data do pedido", example = "01-01-2024 10:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataPedido;

    @Schema(description = "Status do pedido", example = "PENDENTE", allowableValues = { "PENDENTE", "FINALIZADO",
            "CANCELADO", "EM_PREPARACAO", "ENTREGUE" })
    private String status;

    public PedidoResponseDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.dataPedido = pedido.getDataPedido();
        if (pedido.getStatusPedido() != null) {
            this.status = pedido.getStatusPedido().getStatus();
        }
    }
}
