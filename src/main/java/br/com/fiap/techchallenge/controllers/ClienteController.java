package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.ClienteRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.ClienteResponseDTO;
import br.com.fiap.techchallenge.domain.Cliente;
import br.com.fiap.techchallenge.services.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> getAllCliente(){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getClienteById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.getById(id));
    }
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> createCliente(@RequestBody ClienteRequestDTO clienteRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.create(clienteRequestDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> updateCliente(@RequestBody ClienteRequestDTO clienteRequestDTO, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.update(clienteRequestDTO, id));
    }
    @DeleteMapping("/{id}")
    public Object deleteCliente(@PathVariable Long id){
        clienteService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado");
    }

}
