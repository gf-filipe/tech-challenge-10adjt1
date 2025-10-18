package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNome(String nome);
    Optional<Usuario> findByEmail(String email);
}
