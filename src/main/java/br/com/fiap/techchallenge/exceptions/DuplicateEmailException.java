package br.com.fiap.techchallenge.exceptions;

public class DuplicateEmailException extends RuntimeException {
    
    private final String email;
    
    public DuplicateEmailException(String email) {
        super("O email '" + email + "' já está cadastrado no sistema");
        this.email = email;
    }
    
    public DuplicateEmailException(String email, String message) {
        super(message);
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
}
