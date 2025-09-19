package br.com.fiap.techchallenge.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "admin")
@NoArgsConstructor
public class Admin extends Usuario {
    public Admin(String nome, String email, String senha) {
        super(nome, email, senha);
    }
}