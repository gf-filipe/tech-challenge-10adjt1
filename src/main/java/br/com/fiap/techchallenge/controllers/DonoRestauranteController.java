package br.com.fiap.techchallenge.controllers;

import java.net.URI;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.techchallenge.controllers.dto.DonoRestauranteRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.DonoRestauranteResponseDTO;
import br.com.fiap.techchallenge.domain.DonoRestaurante;
import br.com.fiap.techchallenge.services.DonoRestauranteService;

@RestController
@RequestMapping("/v1/dono-restaurante")
@Tag(name = "Donos de Restaurante", description = "Endpoints para gerenciamento de donos de restaurante")
@SecurityRequirement(name = "bearerAuth")
public class DonoRestauranteController {
    private final DonoRestauranteService donoRestauranteService;

    public DonoRestauranteController(DonoRestauranteService donoRestauranteService) {
        this.donoRestauranteService = donoRestauranteService;
    }

    @Operation(summary = "Listar todos os donos de restaurante", description = "Retorna uma lista de todos os donos de restaurante cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de donos de restaurante retornada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = DonoRestauranteResponseDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<DonoRestauranteResponseDTO>> getAllDonoRestaurante() {
        List<DonoRestaurante> donosRestaurante = donoRestauranteService.getAll();
        List<DonoRestauranteResponseDTO> responseDTOs = donosRestaurante.stream()
                .map(DonoRestauranteResponseDTO::fromDomain)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(responseDTOs);
    }

    @Operation(summary = "Buscar dono de restaurante por ID", description = "Retorna um dono de restaurante específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dono de restaurante encontrado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DonoRestauranteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dono de restaurante não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<DonoRestauranteResponseDTO> getDonoRestauranteById(
            @Parameter(description = "ID do dono de restaurante", required = true) @PathVariable Long id) {
        DonoRestaurante donoRestaurante = donoRestauranteService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(DonoRestauranteResponseDTO.fromDomain(donoRestaurante));
    }

    @Operation(summary = "Criar novo dono de restaurante", description = "Cria um novo dono de restaurante no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dono de restaurante criado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DonoRestauranteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<DonoRestauranteResponseDTO> createDonoRestaurante(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do dono de restaurante", required = true, content = @Content(schema = @Schema(implementation = DonoRestauranteRequestDTO.class))) @RequestBody DonoRestauranteRequestDTO donoRestauranteRequestDTO) {
        DonoRestaurante createdDonoRestaurante = donoRestauranteService.create(donoRestauranteRequestDTO);

        URI location = URI.create(String.format("/v1/dono-restaurante/%s", createdDonoRestaurante.getId()));
        DonoRestauranteResponseDTO responseDTO = DonoRestauranteResponseDTO.fromDomain(createdDonoRestaurante);

        return ResponseEntity.status(HttpStatus.CREATED).location(location).body(responseDTO);
    }

    @Operation(summary = "Atualizar dono de restaurante", description = "Atualiza os dados de um dono de restaurante existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dono de restaurante atualizado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DonoRestauranteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dono de restaurante não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<DonoRestauranteResponseDTO> updateDonoRestaurante(
            @Parameter(description = "ID do dono de restaurante", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do dono de restaurante", required = true, content = @Content(schema = @Schema(implementation = DonoRestauranteRequestDTO.class))) @RequestBody DonoRestauranteRequestDTO donoRestauranteRequestDTO) {
        DonoRestaurante updatedDonoRestaurante = donoRestauranteService.update(donoRestauranteRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(DonoRestauranteResponseDTO.fromDomain(updatedDonoRestaurante));
    }

    @Operation(summary = "Excluir dono de restaurante", description = "Remove um dono de restaurante do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dono de restaurante excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dono de restaurante não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonoRestaurante(
            @Parameter(description = "ID do dono de restaurante", required = true) @PathVariable Long id) {
        donoRestauranteService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
