package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.UsuarioDTO;
import br.com.fiap.techchallenge.controllers.dto.UsuarioPasswordDTO;
import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> updateSenha(@PathVariable Long id, @RequestBody UsuarioPasswordDTO senha){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.updateSenha(id, senha));
    }
}
