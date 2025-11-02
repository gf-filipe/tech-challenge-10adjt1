package br.com.fiap.techchallenge.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "DTO para atualização de senha de usuário")
public class UsuarioPasswordDTO {
    @Schema(description = "Senha do usuário", example = "novaSenha123", required = true)
    @NotBlank(message = "Senha é obrigatória")
    private String senha;
}
