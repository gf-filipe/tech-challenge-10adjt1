package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.controllers.dto.EnderecoRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.EnderecoResponseDTO;
import br.com.fiap.techchallenge.domain.Endereco;
import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.exceptions.UserNotFoundException;
import br.com.fiap.techchallenge.repositories.EnderecoRepository;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import br.com.fiap.techchallenge.services.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EnderecoServiceImpl implements EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<EnderecoResponseDTO> findAll() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return enderecos.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public EnderecoResponseDTO findById(Long id) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(() -> new UserNotFoundException("usuário não encontrado"));
        return new EnderecoResponseDTO(endereco);
    }

    @Override
    public EnderecoResponseDTO save(Long idUser, EnderecoRequestDTO enderecoRequestDTO) {
        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(enderecoRequestDTO, endereco);
        Endereco enderecoNovo = enderecoRepository.save(endereco);
        Usuario usuario = usuarioRepository.findById(idUser).orElseThrow(() -> new UserNotFoundException("usuário não encontrado"));
        usuario.setEndereco(enderecoNovo);
        usuarioRepository.save(usuario);
        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO();
        BeanUtils.copyProperties(enderecoNovo, enderecoResponseDTO);
        return enderecoResponseDTO;
    }

    @Override
    public void delete(Long idUser, Long id) {
        Usuario usuario = usuarioRepository.findById(idUser).orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));
        usuario.setEndereco(null);
        usuarioRepository.save(usuario);
        enderecoRepository.deleteById(id);
    }

    @Override
    public EnderecoResponseDTO update(Long id, EnderecoRequestDTO enderecoRequestDTODTO) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Endereço não encontrado"));
        BeanUtils.copyProperties(enderecoRequestDTODTO, endereco);
        enderecoRepository.save(endereco);
        return new EnderecoResponseDTO(endereco);
    }
}
