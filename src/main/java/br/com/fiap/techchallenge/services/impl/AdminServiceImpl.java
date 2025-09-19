package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.repositories.AdminRepository;
import br.com.fiap.techchallenge.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
}
