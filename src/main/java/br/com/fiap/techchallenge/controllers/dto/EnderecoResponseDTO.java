package br.com.fiap.techchallenge.controllers.dto;

import br.com.fiap.techchallenge.domain.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class EnderecoResponseDTO {
    private String rua;
    private String numero;
    private String cidade;
    private String cep;
    private String complemento;


    public EnderecoResponseDTO(Endereco endereco) {
    }
}
