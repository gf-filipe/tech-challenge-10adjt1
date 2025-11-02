package br.com.fiap.techchallenge.config;

import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.techchallenge.exceptions.DuplicateEmailException;
import br.com.fiap.techchallenge.exceptions.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create("https://exemplo.com.br/errors/validation-error"));
        problemDetail.setTitle("Validation Error");
        
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        problemDetail.setDetail("Erro(s) de validação nos campos enviados");
        problemDetail.setProperty("errors", errors);
        
        return problemDetail;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(UserNotFoundException exception) {
        
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setType(URI.create("https://exemplo.com.br/errors/user-not-found"));
        problemDetail.setTitle("User Not Found");
        problemDetail.setDetail(exception.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create("https://exemplo.com.br/errors/invalid-argument"));
        problemDetail.setTitle("Invalid Argument");
        problemDetail.setDetail(exception.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handleResponseStatusException(ResponseStatusException exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(exception.getStatusCode());
        problemDetail.setType(URI.create("https://exemplo.com.br/errors/response-status-exception"));
        problemDetail.setTitle(exception.getReason());
        problemDetail.setDetail(exception.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ProblemDetail handleUsernameNotFoundException(UsernameNotFoundException exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setType(URI.create("https://exemplo.com.br/errors/username-not-found"));
        problemDetail.setTitle("Username Not Found");
        problemDetail.setDetail(exception.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ProblemDetail handleDuplicateEmailException(DuplicateEmailException exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setType(URI.create("https://exemplo.com.br/errors/duplicate-email"));
        problemDetail.setTitle("Email Duplicado");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setProperty("field", "email");
        problemDetail.setProperty("value", exception.getEmail());
        
        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException exception) {

        logger.error("Violação de integridade de dados detectada", exception);

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setType(URI.create("https://exemplo.com.br/errors/data-integrity-violation"));
        problemDetail.setTitle("Conflito de Dados");
        
        String detail = "Não foi possível processar a operação devido a um conflito com os dados existentes";
        
        if (exception.getCause() instanceof SQLIntegrityConstraintViolationException) {
            SQLIntegrityConstraintViolationException sqlException = 
                (SQLIntegrityConstraintViolationException) exception.getCause();
            String message = sqlException.getMessage();
            
            if (message != null) {
                if (message.contains("foreign key constraint") || message.contains("Cannot delete or update a parent row")) {
                    detail = "Não é possível realizar esta operação pois existem registros relacionados";
                }
                else if (message.contains("Cannot add or update a child row")) {
                    detail = "Não é possível realizar esta operação pois a referência informada não existe";
                }
                else if (message.contains("Duplicate entry")) {
                    detail = "Já existe um registro com essas informações no sistema";
                }
            }
        }
        
        problemDetail.setDetail(detail);
        
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception exception) {

        logger.error("Erro inesperado capturado pelo GlobalExceptionHandler", exception);

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setType(URI.create("https://exemplo.com.br/errors/internal-server-error"));
        problemDetail.setTitle("Internal Server Error");
        
        String detail = "Ocorreu um erro inesperado ao processar a solicitação. " +
                       "Por favor, tente novamente mais tarde ou entre em contato com o suporte.";
        
        problemDetail.setDetail(detail);
        
        return problemDetail;
    }
}
