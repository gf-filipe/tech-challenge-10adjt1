package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.Admin;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminResponseDTO {
    private String nome;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataUltimaAlteracao;
    private EnderecoDTO endereco;

    public AdminResponseDTO(Admin Admin) {
        this.nome = Admin.getNome();
        this.email = Admin.getEmail();
        this.dataCriacao = Admin.getDataCriacao();
        this.dataUltimaAlteracao = Admin.getDataUltimaAlteracao();
        if (Admin.getEndereco() != null) {
            this.endereco = new EnderecoDTO();
            BeanUtils.copyProperties(Admin.getEndereco(), this.endereco);
        }
    }


}
