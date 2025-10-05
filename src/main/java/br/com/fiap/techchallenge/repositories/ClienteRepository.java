package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.controllers.dto.ClienteResponseDTO;
import br.com.fiap.techchallenge.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
