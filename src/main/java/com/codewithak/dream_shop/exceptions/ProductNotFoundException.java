package com.codewithak.dream_shop.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
