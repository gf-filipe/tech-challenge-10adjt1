package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.controllers.dto.DonoRestauranteRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.EnderecoRequestDTO;
import br.com.fiap.techchallenge.domain.DonoRestaurante;
import br.com.fiap.techchallenge.domain.Endereco;
import br.com.fiap.techchallenge.exceptions.UserNotFoundException;
import br.com.fiap.techchallenge.repositories.DonoRestauranteRepository;
import br.com.fiap.techchallenge.services.DonoRestauranteService;
import br.com.fiap.techchallenge.services.EnderecoService;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DonoRestauranteServiceImpl implements DonoRestauranteService {
    private final DonoRestauranteRepository donoRestauranteRepository;
    private final PasswordEncoder passwordEncoder;
    private final EnderecoService enderecoService;

    @Override
    public List<DonoRestaurante> getAll() {
        return donoRestauranteRepository.findAll();
    }

    @Override
    public DonoRestaurante getById(Long id) {
        return donoRestauranteRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Override
    public DonoRestaurante create(DonoRestauranteRequestDTO donoRestaurante) {
        DonoRestaurante dono = new DonoRestaurante(
                donoRestaurante.nome(),
                donoRestaurante.email(),
                donoRestaurante.senha()
        );

        dono.setSenha(passwordEncoder.encode(donoRestaurante.senha()));
        dono.setDataCriacao(Instant.now());
        dono.setDataUltimaAlteracao(Instant.now());
        DonoRestaurante donoRestauranteSalvo = donoRestauranteRepository.save(dono);

        if(donoRestaurante.endereco() != null){
            EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO();
            BeanUtils.copyProperties(donoRestaurante.endereco(), enderecoRequestDTO);
            enderecoService.save(donoRestauranteSalvo.getId(), enderecoRequestDTO);
        }
        return donoRestauranteSalvo;
    }

    @Override
    public DonoRestaurante update(DonoRestauranteRequestDTO dono, Long id) {
        DonoRestaurante donoRestaurante = donoRestauranteRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        donoRestaurante.setNome(dono.nome());
        donoRestaurante.setEmail(dono.email());

        if(donoRestaurante.getSenha() != null && !donoRestaurante.getSenha().isEmpty()){
            donoRestaurante.setSenha(passwordEncoder.encode(donoRestaurante.getSenha()));
        }

        if(donoRestaurante.getEndereco() == null){
            Endereco endereco = donoRestaurante.getEndereco() == null ? new Endereco() : donoRestaurante.getEndereco();
            BeanUtils.copyProperties(donoRestaurante.getEndereco(), endereco);
            donoRestaurante.setEndereco(endereco);
        }else{
            donoRestaurante.setEndereco(null);
        }
        donoRestaurante.setDataUltimaAlteracao(Instant.now());
        DonoRestaurante donoRestauranteSalvo = donoRestauranteRepository.save(donoRestaurante);
        return donoRestauranteRepository.save(donoRestauranteSalvo);
    }

    @Override
    public void delete(Long id) {
        donoRestauranteRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        donoRestauranteRepository.deleteById(id);
    }
}
