package br.com.fiap.techchallenge.controllers.dto;

import java.time.LocalTime;

import br.com.fiap.techchallenge.domain.Restaurante;

public record RestauranteResponseDTO(
    Long id,
    String nome,
    String telefone,
    String email,
    String descricao,
    LocalTime horaAbertura,
    LocalTime horaFechamento
) {

    public static RestauranteResponseDTO fromDomain(Restaurante restaurante) {
        return new RestauranteResponseDTO(
            restaurante.getId(),
            restaurante.getNome(),
            restaurante.getTelefone(),
            restaurante.getEmail(),
            restaurante.getDescricao(),
            restaurante.getHoraAbertura(),
            restaurante.getHoraFechamento()
        );
    }
}
