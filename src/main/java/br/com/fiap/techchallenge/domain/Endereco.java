package br.com.fiap.techchallenge.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "rua")
    private String rua;

    @Size(max = 50)
    @Column(name = "numero", length = 50)
    private String numero;

    @Size(max = 100)
    @Column(name = "cidade", length = 100)
    private String cidade;

    @Size(max = 20)
    @Column(name = "cep", length = 20)
    private String cep;

    @Size(max = 255)
    @Column(name = "complemento")
    private String complemento;

}