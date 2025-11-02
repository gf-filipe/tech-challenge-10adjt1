package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.Admin;
import br.com.fiap.techchallenge.domain.Cliente;
import br.com.fiap.techchallenge.domain.DonoRestaurante;
import br.com.fiap.techchallenge.domain.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.Instant;

@Getter
@Setter
@Schema(description = "DTO para resposta de usuário")
public class UsuarioDTO {
    @Schema(description = "Nome do usuário", example = "João Silva")
    private String nome;

    @Schema(description = "Email do usuário", example = "joao.silva@email.com")
    private String email;

    @Schema(description = "Tipo do usuário", example = "CLIENTE", allowableValues = {"ADMIN", "CLIENTE", "DONO_RESTAURANTE"})
    private String tipo;

    @Schema(description = "Data de criação", example = "01-01-2024 10:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataCriacao;

    @Schema(description = "Data da última alteração", example = "01-01-2024 10:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataUltimaAlteracao;

    @Schema(description = "Endereço do usuário")
    private EnderecoDTO endereco;

    public UsuarioDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.dataCriacao = usuario.getDataCriacao();
        this.dataUltimaAlteracao = usuario.getDataUltimaAlteracao();

        if (usuario instanceof Admin) {
            this. tipo = "ADMIN";
        } else if (usuario instanceof Cliente) {
            this.tipo = "CLIENTE";
        } else if (usuario instanceof DonoRestaurante) {
            this.tipo = "DONO_RESTAURANTE";
        }

        if(usuario.getEndereco() != null) {
            this.endereco = new EnderecoDTO();
            BeanUtils.copyProperties(usuario.getEndereco(), this.endereco);
        }
    }

    public UsuarioDTO(){}
}
