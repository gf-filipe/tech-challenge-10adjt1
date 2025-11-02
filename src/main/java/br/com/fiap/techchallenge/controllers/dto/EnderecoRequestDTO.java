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
    @Schema(description = "Nome da rua", example = "Rua das Flores", required = true)
    @NotBlank(message = "Rua é obrigatória")
    private String rua;

    @Schema(description = "Número do endereço", example = "45", required = true)
    @NotBlank(message = "Número é obrigatório")
    private String numero;

    @Schema(description = "Nome da cidade", example = "Curitiba", required = true)
    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @Schema(description = "CEP", example = "80010-010", required = true)
    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
    private String cep;

    @Schema(description = "Complemento do endereço", example = "Apto 101")
    private String complemento;

    public EnderecoRequestDTO(Endereco endereco) {
    }
}
