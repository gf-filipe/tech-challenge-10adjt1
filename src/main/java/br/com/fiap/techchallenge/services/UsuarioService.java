package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.controllers.dto.UsuarioDTO;
import br.com.fiap.techchallenge.controllers.dto.UsuarioPasswordDTO;
import br.com.fiap.techchallenge.domain.Cliente;
import br.com.fiap.techchallenge.domain.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario updateSenha(Long id, UsuarioPasswordDTO usuarioPasswordDTO);

    List<UsuarioDTO> getAll();

    UsuarioDTO getById(Long id);
}
