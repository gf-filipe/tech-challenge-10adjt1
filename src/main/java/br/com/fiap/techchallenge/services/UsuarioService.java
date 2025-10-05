package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.controllers.dto.UsuarioDTO;
import br.com.fiap.techchallenge.controllers.dto.UsuarioPasswordDTO;

import java.util.List;

public interface UsuarioService {

    void updateSenha(Long id, UsuarioPasswordDTO usuarioPasswordDTO);

    List<UsuarioDTO> getAll();

    UsuarioDTO getById(Long id);

    UsuarioDTO getByNome(String nome);
}
