package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.UsuarioDTO;
import br.com.fiap.techchallenge.controllers.dto.UsuarioPasswordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import br.com.fiap.techchallenge.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/usuario")
@Tag(name = "Usuário", description = "Operações de CRUD para usuários")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Operation(summary = "Listar usuários")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuários", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class)))
    })
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getById(id));
    }

    @GetMapping("/busca-nome/{nome}")
    @Operation(summary = "Buscar usuário por nome")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class)))
    })
    public ResponseEntity<UsuarioDTO> getByNome(@PathVariable String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getByNome(nome));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar senha do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso", content = @Content)
    })
    public ResponseEntity<Object> updateSenha(@PathVariable Long id,
            @RequestBody UsuarioPasswordDTO usuarioPasswordDTO) {
        usuarioService.updateSenha(id, usuarioPasswordDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso");
    }
}
