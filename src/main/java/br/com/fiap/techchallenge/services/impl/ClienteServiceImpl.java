package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.controllers.dto.ClienteRequestDTO;
import br.com.fiap.techchallenge.controllers.dto.ClienteResponseDTO;
import br.com.fiap.techchallenge.controllers.dto.EnderecoRequestDTO;
import br.com.fiap.techchallenge.domain.Cliente;
import br.com.fiap.techchallenge.domain.Endereco;
import br.com.fiap.techchallenge.exceptions.UserNotFoundException;
import br.com.fiap.techchallenge.repositories.ClienteRepository;
import br.com.fiap.techchallenge.services.ClienteService;
import br.com.fiap.techchallenge.services.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final EnderecoService enderecoService;

    @Override
    public List<ClienteResponseDTO> getAll() {
        return clienteRepository.findAll().stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO getById(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Cliente não encontrado"));
        return new ClienteResponseDTO(cliente);
    }

    @Override
    public ClienteResponseDTO create(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteRequestDTO, cliente, "senha", "endereco");
        cliente.setSenha(passwordEncoder.encode(clienteRequestDTO.getSenha()));
        cliente.setDataCriacao(Instant.now());
        cliente.setDataUltimaAlteracao(Instant.now());
        Cliente clienteSalvo = clienteRepository.save(cliente);
        if (clienteRequestDTO.getEndereco() != null) {
            EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO();
            BeanUtils.copyProperties(clienteRequestDTO.getEndereco(), enderecoRequestDTO);
            enderecoService.save(clienteSalvo.getId(), enderecoRequestDTO);
        }
        return new ClienteResponseDTO(clienteSalvo);
    }

    @Override
    public ClienteResponseDTO update(ClienteRequestDTO clienteRequestDTO, Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Cliente não encontrado"));
        cliente.setNome(clienteRequestDTO.getNome());
        cliente.setEmail(clienteRequestDTO.getEmail());
        if (clienteRequestDTO.getSenha() != null && !clienteRequestDTO.getSenha().isEmpty()) {
            cliente.setSenha(passwordEncoder.encode(clienteRequestDTO.getSenha()));
        }
        if (clienteRequestDTO.getEndereco() != null) {
            Endereco endereco = cliente.getEndereco() == null ? new Endereco() : cliente.getEndereco();
            BeanUtils.copyProperties(clienteRequestDTO.getEndereco(), endereco);
            cliente.setEndereco(endereco);
        } else {
            cliente.setEndereco(null);
        }
        cliente.setDataUltimaAlteracao(Instant.now());
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return new ClienteResponseDTO(clienteSalvo);
    }

    @Override
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Cliente não encontrado"));
        clienteRepository.delete(cliente);
    }
}