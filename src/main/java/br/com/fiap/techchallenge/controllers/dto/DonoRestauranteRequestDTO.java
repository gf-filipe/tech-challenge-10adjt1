package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.DonoRestaurante;

public record DonoRestauranteRequestDTO(
    String nome, 
    String email, 
    String senha
    ) {


    public DonoRestaurante toDomain() {
        return new DonoRestaurante(nome, email, senha);
    }
}
