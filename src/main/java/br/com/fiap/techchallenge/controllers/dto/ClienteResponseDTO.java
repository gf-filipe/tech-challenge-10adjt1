package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "DTO para resposta de cliente")
public class ClienteResponseDTO {
    @Schema(description = "Nome do cliente", example = "João Silva")
    private String nome;

    @Schema(description = "Email do cliente", example = "joao.silva@email.com")
    private String email;

    @Schema(description = "Data de criação", example = "01-01-2024 10:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataCriacao;

    @Schema(description = "Data da última alteração", example = "01-01-2024 10:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataUltimaAlteracao;

    @Schema(description = "Endereço do cliente")
    private EnderecoDTO endereco;

    @ArraySchema(schema = @Schema(description = "Lista de pedidos do cliente"))
    private List<PedidoResponseDTO> pedidos;

    public ClienteResponseDTO(Cliente cliente) {
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.dataCriacao = cliente.getDataCriacao();
        this.dataUltimaAlteracao = cliente.getDataUltimaAlteracao();
        if (cliente.getEndereco() != null) {
            this.endereco = new EnderecoDTO();
            BeanUtils.copyProperties(cliente.getEndereco(), this.endereco);
        }
        if (cliente.getPedidos() != null && !cliente.getPedidos().isEmpty()) {
            this.pedidos = cliente.getPedidos().stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
        }
    }

}
