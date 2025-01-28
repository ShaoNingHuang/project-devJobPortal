package com.huan1645.TWDevJob.controller;


import com.huan1645.TWDevJob.entity.User;
import com.huan1645.TWDevJob.entity.UserType;
import com.huan1645.TWDevJob.exception.CustomErrorResponse;
import com.huan1645.TWDevJob.exception.EmptyPasswordException;
import com.huan1645.TWDevJob.exception.UserExistedException;
import com.huan1645.TWDevJob.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final UserTypeService userTypeService;

    @Autowired
    public GlobalExceptionHandler(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @ExceptionHandler
    public ModelAndView UserExist(UserExistedException exec){
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("errorMessage", exec.getMessage()); // Add the error message to the model
        List<UserType> userTypes = userTypeService.getAll();
        mav.addObject("userTypes", userTypes);
        mav.addObject("user", new User());
        return mav;
    }
    @ExceptionHandler
    public ModelAndView EmptyPassword(EmptyPasswordException exec){
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("errorMessage", exec.getMessage()); // Add the error message to the model
        List<UserType> userTypes = userTypeService.getAll();
        mav.addObject("userTypes", userTypes);
        mav.addObject("user", new User());
        return mav;
    }

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> GenericException(Exception exec){
        CustomErrorResponse error = new CustomErrorResponse();
        error.setStatusCode(400);
        error.setMessage(exec.getMessage());
        error.setTimestamp(new Date());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
