package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.domain.DonoRestaurante;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DonoRestauranteRepository extends JpaRepository<DonoRestaurante, Long> {
    @Query("SELECT dr FROM DonoRestaurante dr JOIN FETCH dr.endereco")
    List<DonoRestaurante> findAllComEndereco();

    @Query("SELECT dr FROM DonoRestaurante dr JOIN FETCH dr.endereco WHERE dr.id = :id")
    Optional<DonoRestaurante> findByIdComEndereco(Long id);
}
