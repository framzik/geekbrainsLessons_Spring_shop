package ru.khrebtov.shopadminapp.controller;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
