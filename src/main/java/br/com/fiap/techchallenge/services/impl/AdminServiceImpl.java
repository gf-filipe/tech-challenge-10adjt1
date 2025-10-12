package br.com.fiap.techchallenge.services.impl;

import br.com.fiap.techchallenge.controllers.dto.*;
import br.com.fiap.techchallenge.controllers.dto.AdminResponseDTO;
import br.com.fiap.techchallenge.domain.*;
import br.com.fiap.techchallenge.domain.Admin;
import br.com.fiap.techchallenge.domain.Admin;
import br.com.fiap.techchallenge.repositories.AdminRepository;
import br.com.fiap.techchallenge.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository AdminRepository;

    @Override
    public List<AdminResponseDTO> getAll() {
        List<Admin> admins = AdminRepository.findAll();
        return admins.stream()
                .map(AdminResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public AdminResponseDTO getById(Long id) {
        Admin admin = AdminRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin não encontrado"));
        return new AdminResponseDTO(admin);
    }

    @Override
    public AdminResponseDTO create(AdminRequestDTO AdminRequestDTO) {
        Admin Admin = new Admin();
        BeanUtils.copyProperties(AdminRequestDTO, Admin, "endereco");
        if(AdminRequestDTO.getEndereco()!=null){
            Endereco endereco = new Endereco();
            BeanUtils.copyProperties(AdminRequestDTO.getEndereco(), endereco);
            Admin.setEndereco(endereco);
        }
        Admin.setDataCriacao(Instant.now());
        Admin.setDataUltimaAlteracao(Instant.now());
        Admin AdminSalvo = AdminRepository.save(Admin);
        return getAdminResponseDTO(AdminSalvo);
    }

    @Override
    public AdminResponseDTO update(AdminRequestDTO adminRequestDTO, Long id) {
        Admin admin = AdminRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "admin não encontrado"));
        admin.setNome(adminRequestDTO.getNome());
        admin.setEmail(adminRequestDTO.getEmail());
        admin.setSenha(adminRequestDTO.getSenha());

        if(adminRequestDTO.getEndereco()!=null){
            Endereco endereco = admin.getEndereco() == null ?  new Endereco() : admin.getEndereco();
            BeanUtils.copyProperties(adminRequestDTO.getEndereco(), endereco);
            admin.setEndereco(endereco);
        }else{
            admin.setEndereco(null);
        }
        admin.setDataUltimaAlteracao(Instant.now());
        Admin adminSalvo = AdminRepository.save(admin);
        return new AdminResponseDTO(adminSalvo);
    }

    @Override
    public void delete(Long id) {
        AdminRepository.deleteById(id);
    }

    private AdminResponseDTO getAdminResponseDTO(Admin admin) {
        AdminRepository.save(admin);
        AdminResponseDTO AdminResponseDTO = new AdminResponseDTO();
        AdminResponseDTO.setId(admin.getId());
        AdminResponseDTO.setNome(admin.getNome());
        AdminResponseDTO.setEmail(admin.getEmail());
        AdminResponseDTO.setDataCriacao(admin.getDataCriacao());
        AdminResponseDTO.setDataUltimaAlteracao(admin.getDataUltimaAlteracao());
        return AdminResponseDTO;
    }
}
