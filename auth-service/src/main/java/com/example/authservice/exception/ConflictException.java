package com.example.authservice.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) { super(message); }
}
