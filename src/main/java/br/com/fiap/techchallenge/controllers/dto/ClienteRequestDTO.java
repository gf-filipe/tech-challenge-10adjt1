package br.com.fiap.techchallenge.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "DTO para requisição de criação/atualização de cliente")
public class ClienteRequestDTO {

    @Schema(description = "Nome do cliente", example = "Ana Silva", required = true)
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Schema(description = "Email do cliente", example = "ana.cliente@email.com", required = true)
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @Schema(description = "Senha do cliente (obrigatória apenas na criação)", example = "123456")
    private String senha;

    @Schema(description = "Endereço do cliente")
    @Valid
    private EnderecoDTO endereco;
}
