package com.TuT597.movies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(InconsistentIdException.class)
    public ResponseEntity<ProblemDetail> inconsistentIdExceptionHandler() {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "dont touch ids you monkey");
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
