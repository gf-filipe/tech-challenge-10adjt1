package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.ClienteEmailDTO;
import br.com.fiap.techchallenge.controllers.dto.UsuarioDTO;
import br.com.fiap.techchallenge.controllers.dto.UsuarioPasswordDTO;
import br.com.fiap.techchallenge.domain.Cliente;
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
    @GetMapping("/busca-nome/{nome}")
    public ResponseEntity<UsuarioDTO> getByNome(@PathVariable String nome){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getByNome(nome));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateSenha(@PathVariable Long id, @RequestBody UsuarioPasswordDTO usuarioPasswordDTO){
        usuarioService.updateSenha(id, usuarioPasswordDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso");
    }
}
