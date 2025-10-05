package br.com.fiap.techchallenge.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {
    private Long id;
    private String rua;
    private String numero;
    private String cidade;
    private String cep;
    private String complemento;
}
