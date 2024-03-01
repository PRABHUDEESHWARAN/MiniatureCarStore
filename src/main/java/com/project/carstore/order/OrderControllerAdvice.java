package com.project.carstore.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class OrderControllerAdvice {
    @ExceptionHandler(value = {OrderException.class})
    public ResponseEntity<String> handleOrderException(OrderException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
