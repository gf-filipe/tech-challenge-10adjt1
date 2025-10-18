package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.domain.DonoRestaurante;
import br.com.fiap.techchallenge.exceptions.UserNotFoundException;
import br.com.fiap.techchallenge.repositories.DonoRestauranteRepository;
import br.com.fiap.techchallenge.services.DonoRestauranteService;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DonoRestauranteServiceImpl implements DonoRestauranteService {
    private final DonoRestauranteRepository donoRestauranteRepository;

    @Override
    public List<DonoRestaurante> getAll() {
        return donoRestauranteRepository.findAll();
    }

    @Override
    public DonoRestaurante getById(Long id) {
        return donoRestauranteRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Override
    public DonoRestaurante create(DonoRestaurante donoRestaurante) {
        donoRestaurante.setDataCriacao(Instant.now());
        donoRestaurante.setDataUltimaAlteracao(Instant.now());
        return donoRestauranteRepository.save(donoRestaurante);
    }

    @Override
    public DonoRestaurante update(DonoRestaurante donoRestaurante, Long id) {
        donoRestauranteRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        donoRestaurante.setId(id);
        donoRestaurante.setDataUltimaAlteracao(Instant.now());
        return donoRestauranteRepository.save(donoRestaurante);
    }

    @Override
    public void delete(Long id) {
        donoRestauranteRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        donoRestauranteRepository.deleteById(id);
    }
}
