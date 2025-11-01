package br.com.fiap.techchallenge.controllers.dto;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fiap.techchallenge.domain.DonoRestaurante;

public record DonoRestauranteResponseDTO(
    String nome, 
    String email, 
    EnderecoDTO endereco, 
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    Instant dataUltimaAlteracao,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    Instant dataCriacao,
    List<RestauranteResponseDTO> restaurantes
    ) {


    public static DonoRestauranteResponseDTO fromDomain(DonoRestaurante donoRestaurante) {
        EnderecoDTO enderecoDTO = null;
        if (donoRestaurante.getEndereco() != null) {
            enderecoDTO = new EnderecoDTO();
            BeanUtils.copyProperties(donoRestaurante.getEndereco(), enderecoDTO);
        }
        
        return new DonoRestauranteResponseDTO(
            donoRestaurante.getNome(),
            donoRestaurante.getEmail(),
            enderecoDTO,
            donoRestaurante.getDataUltimaAlteracao(),
            donoRestaurante.getDataCriacao(),
            donoRestaurante.getRestaurantes() != null ?
                donoRestaurante.getRestaurantes().stream()
                    .map(RestauranteResponseDTO::fromDomain)
                    .toList() : List.of()
        );
    }
}
