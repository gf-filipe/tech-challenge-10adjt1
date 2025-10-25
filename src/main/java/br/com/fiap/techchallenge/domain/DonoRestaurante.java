package br.com.fiap.techchallenge.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "dono_restaurante")
@NoArgsConstructor
public class DonoRestaurante extends Usuario {
    public DonoRestaurante(String nome, String email, String senha) {
        super(nome, email, senha);
    }
    @OneToMany(mappedBy = "donoRestaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Restaurante> restaurantes;

    public DonoRestaurante(DonoRestaurante donoRestauranteSalvo) {
    }
}