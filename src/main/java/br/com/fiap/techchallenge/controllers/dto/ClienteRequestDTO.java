package br.com.fiap.techchallenge.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ClienteRequestDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private EnderecoDTO endereco;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "America/Sao_Paulo")
    private Instant dataUltimaAlteracao;
}
