package com.zyfgoup.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Zyfgoup
 * @Date 2020/12/5 14:57
 * @Description
 */

@RestController
public class ErrorControllerImpl implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/error")
    public void handlerError(HttpServletRequest request) throws Throwable{
        if (request.getAttribute("javax.servlet.error.exception") != null) {
            throw (Throwable) request.getAttribute("javax.servlet.error.exception");
        }
    }
}
