package br.com.fiap.techchallenge.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "DTO para requisição de criação/atualização de cliente")
public class ClienteRequestDTO {
    @Schema(description = "ID do cliente (apenas para atualização)", example = "1")
    private Long id;

    @Schema(description = "Nome do cliente", example = "João Silva", required = true)
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Schema(description = "Email do cliente", example = "joao.silva@email.com", required = true)
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @Schema(description = "Senha do cliente", example = "senha123", required = true)
    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @Schema(description = "Endereço do cliente")
    @Valid
    private EnderecoDTO endereco;

    @Schema(description = "Data de criação", example = "01-01-2024 10:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataCriacao;

    @Schema(description = "Data da última alteração", example = "01-01-2024 10:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataUltimaAlteracao;
}
