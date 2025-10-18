package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.domain.UserDetailsImpl;
import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Usuário não encontrado com o email: " + username));
        return UserDetailsImpl.build(usuario);
    }
}