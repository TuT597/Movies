package com.TuT597.movies.exceptions;

public class BadInputException extends RuntimeException {
    public BadInputException(String message) {
        super(message);
    }
}