package br.com.fiap.techchallenge.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO com o token de autenticação")
public record TokenDTO(
        @Schema(description = "Token JWT de autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...") String token) {
}
