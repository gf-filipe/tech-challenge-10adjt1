package br.com.fiap.techchallenge.controllers.dto;

import java.time.LocalTime;

import br.com.fiap.techchallenge.domain.Restaurante;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para resposta de restaurante")
public record RestauranteResponseDTO(
    @Schema(description = "ID do restaurante", example = "1")
    Long id,
    
    @Schema(description = "Nome do restaurante", example = "Restaurante Bom Sabor")
    String nome,
    
    @Schema(description = "Telefone do restaurante", example = "(11) 98765-4321")
    String telefone,
    
    @Schema(description = "Email do restaurante", example = "contato@bomsabor.com")
    String email,
    
    @Schema(description = "Descrição do restaurante", example = "Restaurante especializado em comida italiana")
    String descricao,
    
    @Schema(description = "Horário de abertura", example = "10:00")
    LocalTime horaAbertura,
    
    @Schema(description = "Horário de fechamento", example = "22:00")
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
