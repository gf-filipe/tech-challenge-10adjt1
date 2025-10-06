package br.com.fiap.techchallenge.services;

import java.util.List;

import br.com.fiap.techchallenge.domain.DonoRestaurante;

public interface DonoRestauranteService {
    List<DonoRestaurante> getAll();

    DonoRestaurante getById(Long id);

    DonoRestaurante create(DonoRestaurante donoRestaurante);

    DonoRestaurante update(DonoRestaurante donoRestaurante, Long id);

    void delete(Long id);
}
