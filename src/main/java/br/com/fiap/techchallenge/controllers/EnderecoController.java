package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.EnderecoRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.EnderecoResponseDTO;
import br.com.fiap.techchallenge.services.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    private final EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List<EnderecoResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.findById(id));
    }

    @PostMapping("/{id_user}")
    public ResponseEntity<EnderecoResponseDTO> create(@PathVariable(name = "id_user") Long idUser, @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.save(idUser, enderecoRequestDTO));
    }

    @DeleteMapping("/{id_user}/{id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "id_user") Long idUser, @PathVariable Long id) {
        enderecoService.delete(idUser, id);
        return ResponseEntity.status(HttpStatus.OK).body("Endereço deletado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> update(@PathVariable Long id, @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.update(id, enderecoRequestDTO));
    }
}
