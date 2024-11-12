package com.example.controller;

import org.springframework.http.HttpStatus;

public @interface ResponseStatus {

    HttpStatus value();

}
