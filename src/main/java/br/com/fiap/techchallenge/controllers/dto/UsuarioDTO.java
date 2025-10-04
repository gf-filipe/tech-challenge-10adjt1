package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.Admin;
import br.com.fiap.techchallenge.domain.Cliente;
import br.com.fiap.techchallenge.domain.DonoRestaurante;
import br.com.fiap.techchallenge.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String tipo;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();

        if (usuario instanceof Admin) {
            this. tipo = "ADMIN";
        } else if (usuario instanceof Cliente) {
            this.tipo = "CLIENTE";
        } else if (usuario instanceof DonoRestaurante) {
            this.tipo = "DONO_RESTAURANTE";
        }
    }

    public UsuarioDTO(){}
}
