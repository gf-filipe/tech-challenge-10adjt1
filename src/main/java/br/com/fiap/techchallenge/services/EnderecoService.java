package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.controllers.dto.EnderecoDTO;
import br.com.fiap.techchallenge.controllers.dto.EnderecoRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.EnderecoResponseDTO;

import java.util.List;

public interface EnderecoService {
    List<EnderecoResponseDTO> findAll();

    EnderecoResponseDTO findById(Long id);

    EnderecoResponseDTO save(Long idUser, EnderecoRequestDTO enderecoRequestDTO);

    void delete(Long idUser, Long id);

    EnderecoResponseDTO update(Long id, EnderecoRequestDTO enderecoRequestDTO);
}
