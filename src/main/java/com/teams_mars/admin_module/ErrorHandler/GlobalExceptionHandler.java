package com.teams_mars.admin_module.ErrorHandler;


import com.teams_mars.admin_module.dto.ValidationErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/*

import com.example.demo.dto.ValidationErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import java.util.List;
*/
/*1、@ControllerAdvice 表示为应用下所有的控制器给出建议，类中提供 @ExceptionHandler 异常处理方法，
所以组合起来就是为整个应用下的所有控制器给出全局异常处理建议。
2、如同 @RestController 注解组合了 @Controller 与 @ResponseBody 注解一样，@RestControllerAdvice
也组合了 @ControllerAdvice 与 @ResponseBody 注解
3、所以 @ControllerAdvice 中的  @ExceptionHandler 方法返回默认是做页面跳转，加上 @ResponseBody 才会直接返回给页面
 而 @RestControllerAdvice 中的 @ExceptionHandler 方法则默认是返回给页面。*//*


*/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex){
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }
    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors){
        ValidationErrorDTO validationError = new ValidationErrorDTO("ValidationError");
        for (FieldError fieldError: fieldErrors){
            validationError.addFieldError(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return validationError;
    }
}


