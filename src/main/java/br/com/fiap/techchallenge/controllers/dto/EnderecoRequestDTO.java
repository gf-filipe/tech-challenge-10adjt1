package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "DTO para requisição de endereço")
public class EnderecoRequestDTO {
    @Schema(description = "ID do endereço", example = "1")
    private Long id;

    @Schema(description = "Nome da rua", example = "Rua das Flores")
    @NotBlank(message = "Rua é obrigatória")
    private String rua;

    @Schema(description = "Número do endereço", example = "123")
    @NotBlank(message = "Número é obrigatório")
    private String numero;

    @Schema(description = "Nome da cidade", example = "São Paulo")
    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @Schema(description = "CEP", example = "12345-678")
    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
    private String cep;

    @Schema(description = "Complemento do endereço", example = "Apto 123")
    private String complemento;

    public EnderecoRequestDTO(Endereco endereco) {
    }
}
