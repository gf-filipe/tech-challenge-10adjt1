package br.com.fiap.techchallenge.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para realizar login")
public record LoginDTO(
        @Schema(description = "Email do usuário", example = "admin@techchallenge.com") @NotBlank(message = "Email é obrigatório") @Email(message = "Formato de email inválido") String email,

        @Schema(description = "Senha do usuário", example = "123456") @NotBlank(message = "Senha é obrigatória") String senha) {
}
