package br.com.spa.demoparkapi.exception;

public class PasswordInvalidException extends RuntimeException{

    public PasswordInvalidException(String message){
        super(message);
    }

    public PasswordInvalidException(String message, Long id){
        super(message);
    }

}
