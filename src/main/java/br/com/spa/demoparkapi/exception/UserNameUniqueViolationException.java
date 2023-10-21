package br.com.spa.demoparkapi.exception;

public class UserNameUniqueViolationException extends RuntimeException{

    public UserNameUniqueViolationException(String message){
        super(message);
    }

}
