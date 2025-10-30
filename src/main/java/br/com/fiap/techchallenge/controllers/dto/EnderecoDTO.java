package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "DTO para endereço")
public class EnderecoDTO {
    @Schema(description = "ID do endereço", example = "1")
    private Long id;

    @Schema(description = "Nome da rua", example = "Av. Paulista", required = true)
    @NotBlank(message = "Rua é obrigatória")
    private String rua;

    @Schema(description = "Número", example = "1000", required = true)
    @NotBlank(message = "Número é obrigatório")
    private String numero;

    @Schema(description = "Cidade", example = "São Paulo", required = true)
    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @Schema(description = "CEP", example = "01310-100", required = true)
    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido")
    private String cep;

    @Schema(description = "Complemento", example = "Apto 123")
    private String complemento;

    public EnderecoDTO(Endereco endereco) {
    }
}
