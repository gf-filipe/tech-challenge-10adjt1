package br.com.fiap.techchallenge.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO para atualização de nome de usuário")
public class UsuarioNomeDTO {
    @Schema(description = "Nome do usuário", example = "Ana Silva Atualizada", required = true)
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
}
