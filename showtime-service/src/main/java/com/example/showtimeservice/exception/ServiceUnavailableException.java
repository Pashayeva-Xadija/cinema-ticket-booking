package com.example.showtimeservice.exception;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String msg){
        super(msg);
    } }
