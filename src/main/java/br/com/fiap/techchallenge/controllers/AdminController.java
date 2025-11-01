package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.AdminRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.AdminResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import br.com.fiap.techchallenge.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/admin")
@Tag(name = "Admin", description = "Operações administrativas")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {
    private final AdminService AdminService;

    @Operation(summary = "Listar administradores", description = "Retorna uma lista de todos os administradores cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de administradores retornada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = AdminResponseDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> getAlladmin() {
        return ResponseEntity.status(HttpStatus.OK).body(AdminService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar administrador por ID", description = "Retorna um administrador específico pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador encontrado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AdminResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    public ResponseEntity<AdminResponseDTO> getadminById(
            @Parameter(description = "ID do administrador", required = true) @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(AdminService.getById(id));
    }

    @Operation(summary = "Criar novo administrador", description = "Cria um novo administrador no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Administrador criado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AdminResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AdminResponseDTO> createadmin(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do administrador", required = true, content = @Content(schema = @Schema(implementation = AdminRequestDTO.class))) @RequestBody AdminRequestDTO adminRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(AdminService.create(adminRequestDTO));
    }

    @Operation(summary = "Atualizar administrador", description = "Atualiza os dados de um administrador existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador atualizado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AdminResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> updateadmin(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do administrador", required = true, content = @Content(schema = @Schema(implementation = AdminRequestDTO.class))) @RequestBody AdminRequestDTO adminRequestDTO,
            @Parameter(description = "ID do administrador", required = true) @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(AdminService.update(adminRequestDTO, id));
    }

    @Operation(summary = "Excluir administrador", description = "Remove um administrador do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteadmin(
            @Parameter(description = "ID do administrador", required = true) @PathVariable Long id) {
        AdminService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Admin deletado");
    }

}
