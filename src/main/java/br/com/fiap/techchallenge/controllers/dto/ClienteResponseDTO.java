package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ClienteResponseDTO {
    private Long id;
    private String nome;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataUltimaAlteracao;
    private EnderecoDTO endereco;
    private List<PedidoResponseDTO> pedidos;

    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.dataCriacao = cliente.getDataCriacao();
        this.dataUltimaAlteracao = cliente.getDataUltimaAlteracao();
        if (cliente.getEndereco() != null) {
            this.endereco = new EnderecoDTO();
            BeanUtils.copyProperties(cliente.getEndereco(), this.endereco);
        }
        if(cliente.getPedidos() != null && !cliente.getPedidos().isEmpty()) {
            this.pedidos = cliente.getPedidos().stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
        }
    }


}
