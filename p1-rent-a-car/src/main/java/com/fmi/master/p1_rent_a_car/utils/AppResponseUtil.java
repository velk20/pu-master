package com.fmi.master.p1_rent_a_car.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppResponseUtil {

    private static HashMap<String, Object> response;

    public static AppResponseUtil deleted() {

        response = new HashMap<>();
        response.put("status", "deleted");
        response.put("code", HttpStatus.NO_CONTENT.value());
        return new AppResponseUtil();
    }

    public static AppResponseUtil created() {

        response = new HashMap<>();
        response.put("status", "created");
        response.put("code", HttpStatus.CREATED.value());
        return new AppResponseUtil();
    }

    public static AppResponseUtil success() {

        response = new HashMap<>();
        response.put("status", "success");
        response.put("code", HttpStatus.OK.value());
        return new AppResponseUtil();
    }

    public static AppResponseUtil error(HttpStatus status) {

        response = new HashMap<>();
        response.put("status", "error");
        response.put("code", status.value());
        return new AppResponseUtil();
    }


    // метод за статус код
    public AppResponseUtil withCode(HttpStatus code) {
        response.put("code", code.value());
        return this;
    }

    // метод за съобщения
    public AppResponseUtil withMessage(String message) {
        response.put("message", message);
        return this;
    }

    public AppResponseUtil withErrors(List<String> errors){
        response.put("errors", errors);
        return this;
    }

    public AppResponseUtil withDetailedMessage(String message) {
        response.put("detailed-message", message);
        return this;
    }

    public AppResponseUtil withStackTrace(String stackTrace) {
        response.put("stack-trace", stackTrace);
        return this;
    }

    public AppResponseUtil withData(Object data) {
        List<Object> list = new ArrayList<>();
        if (!(data instanceof List<?>)) {
            list.add(data);
        } else {
            list = (List<Object>) data;
        }
        response.put("data", list);
        return this;
    }

    public ResponseEntity<Object> build() {

        int code = (int) response.get("code");
        return new ResponseEntity<Object>(response, HttpStatusCode.valueOf(code));
    }
}