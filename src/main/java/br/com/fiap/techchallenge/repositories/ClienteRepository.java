package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
