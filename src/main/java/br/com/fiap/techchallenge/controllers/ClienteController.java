package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.ClienteDTO;
import br.com.fiap.techchallenge.controllers.dto.ClienteEmailDTO;
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
    public ResponseEntity<List<ClienteDTO>> getAllCliente(){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.getById(id));
    }
    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody ClienteDTO clienteDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.create(clienteDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@RequestBody ClienteDTO clienteDTO, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.update(clienteDTO, id));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> patchSenhaCliente(@RequestBody ClienteEmailDTO clienteEmailDTO, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.updateEmail(clienteEmailDTO, id));
    }
    @DeleteMapping("/{id}")
    public Object deleteCliente(@PathVariable Long id){
        clienteService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado");
    }

}
