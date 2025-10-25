package br.com.fiap.techchallenge.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.techchallenge.controllers.dto.DonoRestauranteRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.DonoRestauranteResponseDTO;
import br.com.fiap.techchallenge.domain.DonoRestaurante;
import br.com.fiap.techchallenge.services.DonoRestauranteService;

@RestController
@RequestMapping("/dono-restaurante")
public class DonoRestauranteController {
    private final DonoRestauranteService donoRestauranteService;

    public DonoRestauranteController(DonoRestauranteService donoRestauranteService) {
        this.donoRestauranteService = donoRestauranteService;
    }

    @GetMapping
    public ResponseEntity<List<DonoRestauranteResponseDTO>> getAllDonoRestaurante() {
        List<DonoRestaurante> donosRestaurante = donoRestauranteService.getAll();
        List<DonoRestauranteResponseDTO> responseDTOs = donosRestaurante.stream()
                .map(DonoRestauranteResponseDTO::fromDomain)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonoRestauranteResponseDTO> getDonoRestauranteById(@PathVariable Long id) {
        DonoRestaurante donoRestaurante = donoRestauranteService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(DonoRestauranteResponseDTO.fromDomain(donoRestaurante));
    }

    @PostMapping
    public ResponseEntity<DonoRestauranteResponseDTO> createDonoRestaurante(@RequestBody DonoRestauranteRequestDTO donoRestauranteRequestDTO) {
        DonoRestaurante createdDonoRestaurante = donoRestauranteService.create(donoRestauranteRequestDTO);

        URI location = URI.create(String.format("/v1/dono-restaurante/%s", createdDonoRestaurante.getId()));
        DonoRestauranteResponseDTO responseDTO = DonoRestauranteResponseDTO.fromDomain(createdDonoRestaurante);

        return ResponseEntity.status(HttpStatus.CREATED).location(location).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonoRestauranteResponseDTO> updateDonoRestaurante(@PathVariable Long id, @RequestBody DonoRestauranteRequestDTO donoRestauranteRequestDTO) {
        DonoRestaurante updatedDonoRestaurante = donoRestauranteService.update(donoRestauranteRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(DonoRestauranteResponseDTO.fromDomain(updatedDonoRestaurante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonoRestaurante(@PathVariable Long id) {
        donoRestauranteService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
