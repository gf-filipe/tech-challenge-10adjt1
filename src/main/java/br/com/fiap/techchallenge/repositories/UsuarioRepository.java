package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.controllers.dto.ClienteDTO;
import br.com.fiap.techchallenge.controllers.dto.UsuarioDTO;
import br.com.fiap.techchallenge.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
