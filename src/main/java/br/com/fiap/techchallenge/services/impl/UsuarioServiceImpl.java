package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.controllers.dto.UsuarioDTO;
import br.com.fiap.techchallenge.controllers.dto.UsuarioPasswordDTO;
import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import br.com.fiap.techchallenge.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {
    UsuarioRepository usuarioRepository;

    @Override
    public void updateSenha(Long id, UsuarioPasswordDTO usuarioPasswordDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        if(usuario.getSenha().equals(usuarioPasswordDTO.getSenha())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A nova senha não pode ser igual à senha antiga.");
        }else{
            usuario.setSenha(usuarioPasswordDTO.getSenha());
            usuario.setDataUltimaAlteracao(Instant.now());
            usuarioRepository.save(usuario);
        }
    }

    @Override
    public List<UsuarioDTO> getAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO getById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        return new UsuarioDTO(usuario);
    }

    @Override
    public UsuarioDTO getByNome(String nome) {
        Usuario usuario = usuarioRepository.findByNome(nome);
        if(usuario == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuário não encontrado.");
        }
        return new UsuarioDTO(usuario);
    }
}
