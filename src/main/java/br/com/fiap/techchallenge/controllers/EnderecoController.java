package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.EnderecoRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.EnderecoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import br.com.fiap.techchallenge.services.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/endereco")
@Tag(name = "Endereço", description = "Operações de CRUD para endereços")
@SecurityRequirement(name = "bearerAuth")
public class EnderecoController {
    private final EnderecoService enderecoService;

    @GetMapping
    @Operation(summary = "Listar endereços", description = "Retorna uma lista de todos os endereços cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = EnderecoResponseDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    public ResponseEntity<List<EnderecoResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar endereço por ID", description = "Retorna um endereço específico pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EnderecoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    public ResponseEntity<EnderecoResponseDTO> findById(
            @Parameter(description = "ID do endereço", required = true) @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.findById(id));
    }

    @PostMapping("/{id_user}")
    @Operation(summary = "Criar endereço para usuário", description = "Cria um novo endereço associado a um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EnderecoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    public ResponseEntity<EnderecoResponseDTO> create(
            @Parameter(description = "ID do usuário", required = true) @PathVariable(name = "id_user") Long idUser,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do endereço", required = true, content = @Content(schema = @Schema(implementation = EnderecoRequestDTO.class))) @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.save(idUser, enderecoRequestDTO));
    }

    @DeleteMapping("/{id_user}/{id}")
    @Operation(summary = "Remover endereço de usuário", description = "Remove um endereço específico de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço ou usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    public ResponseEntity<String> delete(
            @Parameter(description = "ID do usuário", required = true) @PathVariable(name = "id_user") Long idUser,
            @Parameter(description = "ID do endereço", required = true) @PathVariable Long id) {
        enderecoService.delete(idUser, id);
        return ResponseEntity.status(HttpStatus.OK).body("Endereço deletado");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar endereço", description = "Atualiza os dados de um endereço existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EnderecoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    public ResponseEntity<EnderecoResponseDTO> update(
            @Parameter(description = "ID do endereço", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do endereço", required = true, content = @Content(schema = @Schema(implementation = EnderecoRequestDTO.class))) @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.update(id, enderecoRequestDTO));
    }
}
