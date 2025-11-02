package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.Admin;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "DTO para resposta de administrador")
public class AdminResponseDTO {
    @Schema(description = "ID do administrador", example = "1")
    private Long id;

    @Schema(description = "Nome do administrador", example = "Maria Souza")
    private String nome;

    @Schema(description = "Email do administrador", example = "maria.souza@email.com")
    private String email;

    @Schema(description = "Data de criação", example = "01-01-2024 10:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataCriacao;

    @Schema(description = "Data da última alteração", example = "01-01-2024 10:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataUltimaAlteracao;

    @Schema(description = "Endereço do administrador")
    private EnderecoDTO endereco;

    public AdminResponseDTO(Admin Admin) {
        this.id = Admin.getId();
        this.nome = Admin.getNome();
        this.email = Admin.getEmail();
        this.dataCriacao = Admin.getDataCriacao();
        this.dataUltimaAlteracao = Admin.getDataUltimaAlteracao();
        if (Admin.getEndereco() != null) {
            this.endereco = new EnderecoDTO();
            BeanUtils.copyProperties(Admin.getEndereco(), this.endereco);
        }
    }


}
