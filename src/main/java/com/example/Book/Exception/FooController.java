package com.example.Book.Exception;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class FooController extends Exception {
    public FooController(String msg) {
        super(msg);
    }
}
