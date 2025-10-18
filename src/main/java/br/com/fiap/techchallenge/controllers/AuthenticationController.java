package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.LoginDTO;
import br.com.fiap.techchallenge.controllers.dto.TokenDTO;
import br.com.fiap.techchallenge.services.impl.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/gerar-hash")//temporario
    public String gerarHash(@RequestParam String senha) {
        return passwordEncoder.encode(senha);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(authToken);
        var tokenJWT = tokenService.gerarToken((UserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }

}
