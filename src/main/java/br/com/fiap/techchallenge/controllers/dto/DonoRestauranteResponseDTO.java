package br.com.fiap.techchallenge.controllers.dto;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fiap.techchallenge.domain.DonoRestaurante;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

@Schema(description = "DTO para resposta de dono de restaurante")
public record DonoRestauranteResponseDTO(
    @Schema(description = "Nome do dono do restaurante", example = "João da Silva")
    String nome, 
    
    @Schema(description = "Email do dono do restaurante", example = "joao.silva@email.com")
    String email, 
    
    @Schema(description = "Endereço do dono do restaurante")
    EnderecoDTO endereco, 
    
    @Schema(description = "Data da última alteração", example = "01-01-2024 10:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    Instant dataUltimaAlteracao,
    
    @Schema(description = "Data de criação", example = "01-01-2024 10:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    Instant dataCriacao,
    
    @ArraySchema(schema = @Schema(description = "Lista de restaurantes do dono"))
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
