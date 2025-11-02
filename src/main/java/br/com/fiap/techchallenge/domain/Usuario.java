package br.com.fiap.techchallenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "usuario")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "senha", nullable = false)
    private String senha;

    @Transient
    private String senhaOld;

    @Column(name = "data_criacao")
    private Instant dataCriacao;

    @Column(name = "data_ultima_alteracao")
    private Instant dataUltimaAlteracao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    public Usuario(String nome, String email, String senha) {
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setDataUltimaAlteracao(Instant.now());
    }

    public Usuario(String nome, String email, String senha, Endereco endereco) {
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setDataUltimaAlteracao(Instant.now());
    }
}