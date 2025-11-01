package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.AdminRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.AdminResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import br.com.fiap.techchallenge.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/admin")
@Tag(name = "Admin", description = "Operações administrativas")
public class AdminController {
    private final AdminService AdminService;

    @Operation(summary = "Listar administradores")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de administradores", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> getAlladmin() {
        return ResponseEntity.status(HttpStatus.OK).body(AdminService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar administrador por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado", content = @Content)
    })
    public ResponseEntity<AdminResponseDTO> getadminById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(AdminService.getById(id));
    }

    @Operation(summary = "Criar administrador")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Administrador criado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<AdminResponseDTO> createadmin(@RequestBody AdminRequestDTO adminRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(AdminService.create(adminRequestDTO));
    }

    @Operation(summary = "Atualizar administrador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador atualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> updateadmin(@RequestBody AdminRequestDTO adminRequestDTO,
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(AdminService.update(adminRequestDTO, id));
    }

    @Operation(summary = "Remover administrador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador removido", content = @Content)
    })
    @DeleteMapping("/{id}")
    public Object deleteadmin(@PathVariable Long id) {
        AdminService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("admin deletado");
    }

}
