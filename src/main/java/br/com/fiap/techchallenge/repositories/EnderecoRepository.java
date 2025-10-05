package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
