package br.com.fiap.techchallenge.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para requisição de criação/atualização de dono de restaurante")
public record DonoRestauranteRequestDTO(
        @Schema(description = "Nome do dono do restaurante", example = "Carlos Pereira") @NotBlank(message = "Nome é obrigatório") String nome,

        @Schema(description = "Email do dono do restaurante", example = "carlos.dono@email.com") @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,

        @Schema(description = "Senha do dono do restaurante", example = "123456") @NotBlank(message = "Senha é obrigatória") String senha,

        @Schema(description = "Endereço do dono do restaurante") @Valid EnderecoRequestDTO endereco) {

}
