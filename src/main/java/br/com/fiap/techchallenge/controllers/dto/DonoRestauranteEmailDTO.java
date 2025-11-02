package br.com.fiap.techchallenge.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para consulta de dono de restaurante por email")
public record DonoRestauranteEmailDTO(
        @Schema(description = "Email do dono do restaurante", example = "carlos.dono@email.com", required = true)
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email) {
}
