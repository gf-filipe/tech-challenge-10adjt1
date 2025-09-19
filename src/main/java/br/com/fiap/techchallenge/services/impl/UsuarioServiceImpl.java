package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import br.com.fiap.techchallenge.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {
    UsuarioRepository usuarioRepository;
}
