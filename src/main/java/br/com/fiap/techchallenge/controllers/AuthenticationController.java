package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.LoginDTO;
import br.com.fiap.techchallenge.controllers.dto.TokenDTO;
import br.com.fiap.techchallenge.services.impl.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints relacionados à autenticação de usuários")
public class AuthenticationController {

    private final AuthenticationManager manager;

    private final TokenService tokenService;

    @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna um token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas - e-mail ou senha incorretos", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Dados de login inválidos", content = @Content)
    })
    @PostMapping("/v1/login")
    public ResponseEntity<Object> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciais de login (email e senha)", required = true, content = @Content(schema = @Schema(implementation = LoginDTO.class))) @RequestBody @Valid LoginDTO dados) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
            var authentication = manager.authenticate(authToken);
            var tokenJWT = tokenService.gerarToken((UserDetails) authentication.getPrincipal());
            return ResponseEntity.ok(new TokenDTO(tokenJWT));
        } catch (AuthenticationException e) {
            System.err.println("Falha na autenticação: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas: e-mail ou senha incorretos.");
        }
    }

}
