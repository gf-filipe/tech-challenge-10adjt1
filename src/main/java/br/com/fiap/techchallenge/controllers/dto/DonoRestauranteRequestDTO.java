package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.DonoRestaurante;
import br.com.fiap.techchallenge.domain.Endereco;

public record DonoRestauranteRequestDTO(
    String nome, 
    String email, 
    String senha,
    EnderecoRequestDTO endereco
    ) {


}
