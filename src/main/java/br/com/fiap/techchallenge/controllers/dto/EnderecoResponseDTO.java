package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "DTO para resposta de endereço")
public class EnderecoResponseDTO {
    @Schema(description = "Nome da rua", example = "Av. Paulista")
    private String rua;

    @Schema(description = "Número", example = "1000")
    private String numero;

    @Schema(description = "Cidade", example = "São Paulo")
    private String cidade;

    @Schema(description = "CEP", example = "01310-100")
    private String cep;

    @Schema(description = "Complemento", example = "Apto 123")
    private String complemento;


    public EnderecoResponseDTO(Endereco endereco) {
    }
}
