package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.domain.DonoRestaurante;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonoRestauranteRepository extends JpaRepository<DonoRestaurante, Long> {
 
}
