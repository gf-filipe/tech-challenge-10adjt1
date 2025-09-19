package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.controllers.dto.ClienteDTO;
import br.com.fiap.techchallenge.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c")
    List<ClienteDTO> findAllClienteDTO();

    @Query("SELECT c FROM Cliente c WHERE c.id = :id")
    Optional<ClienteDTO> findByIdClienteDTO(Long id);


    @Query("SELECT c FROM Cliente c JOIN FETCH c.endereco")
    List<Cliente> findAllComEndereco();

    @Query("SELECT c FROM Cliente c JOIN FETCH c.endereco WHERE c.id = :id")
    Optional<Cliente> findByIdComEndereco(Long id);

}
