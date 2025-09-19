package br.com.fiap.techchallenge.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "restaurante")
public class Restaurante {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Size(max = 20)
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "hora_abertura")
    private LocalTime horaAbertura;

    @Column(name = "hora_fechamento")
    private LocalTime horaFechamento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_dono_restaurante", nullable = false)
    private DonoRestaurante donoRestaurante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status_restaurante")
    private StatusRestaurante statusRestaurante;

}