package com.hcely.tareas.exception;

public class TareaNotFoundException extends RuntimeException {
    public TareaNotFoundException(String message) {
        super(message);
    }
}

