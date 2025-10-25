package br.com.fiap.techchallenge.services;

import java.util.List;

import br.com.fiap.techchallenge.controllers.dto.DonoRestauranteRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.DonoRestauranteResponseDTO;
import br.com.fiap.techchallenge.domain.DonoRestaurante;

public interface DonoRestauranteService {
    List<DonoRestaurante> getAll();

    DonoRestaurante getById(Long id);

    DonoRestaurante create(DonoRestauranteRequestDTO donoRestaurante);

    DonoRestaurante update(DonoRestauranteRequestDTO donoRestaurante, Long id);

    void delete(Long id);
}
