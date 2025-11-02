package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.UsuarioDTO;
import br.com.fiap.techchallenge.controllers.dto.UsuarioNomeDTO;
import br.com.fiap.techchallenge.controllers.dto.UsuarioPasswordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import br.com.fiap.techchallenge.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/usuario")
@Tag(name = "Usuário", description = "Operações de CRUD para usuários")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Operation(summary = "Listar usuários", description = "Retorna uma lista de todos os usuários cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    public ResponseEntity<UsuarioDTO> getById(
            @Parameter(description = "ID do usuário", required = true) @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getById(id));
    }

    @GetMapping("/busca-nome")
    @Operation(summary = "Buscar usuário por nome", description = "Retorna uma lista de usuários que correspondem ao nome informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuários encontrados com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    public ResponseEntity<List<UsuarioDTO>> getByNome(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados contendo o nome do usuário para busca", required = true, content = @Content(schema = @Schema(implementation = UsuarioNomeDTO.class))) @Valid @RequestBody UsuarioNomeDTO usuario) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getByNome(usuario));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar senha do usuário", description = "Atualiza a senha de um usuário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Proibido", content = @Content)
    })
    public ResponseEntity<String> updateSenha(
            @Parameter(description = "ID do usuário", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nova senha do usuário", required = true, content = @Content(schema = @Schema(implementation = UsuarioPasswordDTO.class))) @Valid @RequestBody UsuarioPasswordDTO usuarioPasswordDTO) {
        usuarioService.updateSenha(id, usuarioPasswordDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso");
    }
}
