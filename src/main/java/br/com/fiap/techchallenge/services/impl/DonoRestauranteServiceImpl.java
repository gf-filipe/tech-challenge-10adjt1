package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.controllers.dto.DonoRestauranteEmailDTO;
import br.com.fiap.techchallenge.controllers.dto.DonoRestauranteResponseDTO;
import br.com.fiap.techchallenge.domain.DonoRestaurante;
import br.com.fiap.techchallenge.repositories.DonoRestauranteRepository;
import br.com.fiap.techchallenge.services.DonoRestauranteService;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DonoRestauranteServiceImpl implements DonoRestauranteService {
    private final DonoRestauranteRepository donoRestauranteRepository;

    @Override
    public List<DonoRestaurante> getAll() {
        return donoRestauranteRepository.findAllComEndereco();
    }

    @Override
    public DonoRestaurante getById(Long id) {
        return donoRestauranteRepository.findByIdComEndereco(id).orElse(null);
    }

    @Override
    public DonoRestaurante create(DonoRestaurante donoRestaurante) {
        return donoRestauranteRepository.save(donoRestaurante);
    }

    @Override
    public DonoRestaurante update(DonoRestaurante donoRestaurante, Long id) {
        donoRestaurante.setId(id);
        return donoRestauranteRepository.save(donoRestaurante);
    }

    @Override
    public DonoRestaurante updateEmail(DonoRestauranteEmailDTO donoRestauranteEmailDTO, Long id) {
        DonoRestaurante donoRestaurante = donoRestauranteRepository.findByIdComEndereco(id).orElseThrow();
        donoRestaurante.setEmail(donoRestauranteEmailDTO.email());
        return donoRestauranteRepository.save(donoRestaurante);
    }

    @Override
    public void delete(Long id) {
        donoRestauranteRepository.deleteById(id);
    }
}
