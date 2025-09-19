package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.repositories.DonoRestauranteRepository;
import br.com.fiap.techchallenge.services.DonoRestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DonoRestauranteServiceImpl implements DonoRestauranteService {
    DonoRestauranteRepository donoRestauranteRepository;
}
