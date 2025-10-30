package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.EnderecoRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.EnderecoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import br.com.fiap.techchallenge.services.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/endereco")
@Tag(name = "Endereço", description = "Operações de CRUD para endereços")
public class EnderecoController {
    private final EnderecoService enderecoService;

    @GetMapping
    @Operation(summary = "Listar endereços")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de endereços", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoResponseDTO.class)))
    })
    public ResponseEntity<List<EnderecoResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar endereço por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoResponseDTO.class)))
    })
    public ResponseEntity<EnderecoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.findById(id));
    }

    @PostMapping("/{id_user}")
    @Operation(summary = "Criar endereço para usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço criado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoResponseDTO.class)))
    })
    public ResponseEntity<EnderecoResponseDTO> create(@PathVariable(name = "id_user") Long idUser,
            @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.save(idUser, enderecoRequestDTO));
    }

    @DeleteMapping("/{id_user}/{id}")
    @Operation(summary = "Remover endereço de usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço removido", content = @Content)
    })
    public ResponseEntity<Object> delete(@PathVariable(name = "id_user") Long idUser, @PathVariable Long id) {
        enderecoService.delete(idUser, id);
        return ResponseEntity.status(HttpStatus.OK).body("Endereço deletado");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar endereço")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço atualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoResponseDTO.class)))
    })
    public ResponseEntity<EnderecoResponseDTO> update(@PathVariable Long id,
            @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.update(id, enderecoRequestDTO));
    }
}
