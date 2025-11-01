package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.Admin;
import br.com.fiap.techchallenge.domain.Cliente;
import br.com.fiap.techchallenge.domain.DonoRestaurante;
import br.com.fiap.techchallenge.domain.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.Instant;

@Getter
@Setter
public class UsuarioDTO {
    private String nome;
    private String email;
    private String tipo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataUltimaAlteracao;
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
