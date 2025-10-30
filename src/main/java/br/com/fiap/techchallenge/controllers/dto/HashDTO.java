package br.com.fiap.techchallenge.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO com o hash BCrypt gerado para uma senha")
public record HashDTO(
        @Schema(description = "Hash BCrypt da senha", example = "$2a$10$7Qe...") String hash) {
}
