package br.com.fiap.techchallenge.controllers.dto;

import java.time.Instant;

import br.com.fiap.techchallenge.domain.DonoRestaurante;
import br.com.fiap.techchallenge.domain.Endereco;

public record DonoRestauranteResponseDTO(Long id, String nome, String email, Endereco endereco, Instant dataUltimaAlteracao) {
    public static DonoRestauranteResponseDTO fromDomain(DonoRestaurante donoRestaurante) {
        return new DonoRestauranteResponseDTO(
            donoRestaurante.getId(),
            donoRestaurante.getNome(),
            donoRestaurante.getEmail(),
            donoRestaurante.getEndereco(),
            donoRestaurante.getDataUltimaAlteracao()
        );
    }
}
