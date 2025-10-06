package br.com.fiap.techchallenge.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cliente")
@NoArgsConstructor
public class Cliente extends Usuario {
    public Cliente(String nome, String email, String senha) {
        super(nome, email, senha);
    }
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pedido> pedidos;
}