package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.controllers.dto.AdminRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.AdminResponseDTO;
import br.com.fiap.techchallenge.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService AdminService;
    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> getAlladmin(){
        return ResponseEntity.status(HttpStatus.OK).body(AdminService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> getadminById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(AdminService.getById(id));
    }
    @PostMapping
    public ResponseEntity<AdminResponseDTO> createadmin(@RequestBody AdminRequestDTO adminRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(AdminService.create(adminRequestDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> updateadmin(@RequestBody AdminRequestDTO adminRequestDTO, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(AdminService.update(adminRequestDTO, id));
    }
    @DeleteMapping("/{id}")
    public Object deleteadmin(@PathVariable Long id){
        AdminService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("admin deletado");
    }

}
