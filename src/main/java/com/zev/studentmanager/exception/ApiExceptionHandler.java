package com.zev.studentmanager.exception;


import com.zev.studentmanager.dto.response.ApiResponse;
import com.zev.studentmanager.enums.MessageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<?> handlingException(Exception exception) {
        log.error("error - {}", exception.getMessage());

        return ApiResponse.build()
                .withSuccess(false)
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCode(exception.hashCode())
                .withMessage(exception.getMessage())
                .toEntity();
    }
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<?> handlingRuntimeException(RuntimeException exception) {
        log.error("error - {}", exception.getMessage());

        return ApiResponse.build()
                .withSuccess(false)
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCode(exception.hashCode())
                .withMessage(exception.getMessage())
                .toEntity();
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<?> handlingAppException(AppException exception) {
        log.error("error - {}", exception.getMessage());

        MessageCode messageCode = exception.getMessageCode();
        return ApiResponse.build()
                .withSuccess(false)
                .withHttpStatus(messageCode.getStatusCode())
                .withCode(messageCode.getCode())
                .withMessage(messageCode.getMessage())
                .toEntity();
    }

    @ExceptionHandler(value = ResourceNotFound.class)
    ResponseEntity<?> handlingResourceException(ResourceNotFound exception) {
        MessageCode messageCode = MessageCode.RESOURCE_NOT_FOUND;
        log.error("error - {}", messageCode.getMessage());

        return ApiResponse.build()
                .withSuccess(false)
                .withHttpStatus(messageCode.getStatusCode())
                .withCode(messageCode.getCode())
                .withMessage(messageCode.getMessage())
                .toEntity();
    }

        @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<?> handlingAccessDeniedException(AccessDeniedException exception) {
        MessageCode messageCode = MessageCode.UNAUTHORIZED;
        log.error("error - {}", exception.getMessage());

        return ApiResponse.build()
                .withSuccess(false)
                .withHttpStatus(messageCode.getStatusCode())
                .withCode(messageCode.getCode())
                .withMessage(messageCode.getMessage())
                .toEntity();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<?> handlingValidation(MethodArgumentNotValidException exception) {
        MessageCode messageCode = MessageCode.INVALID_KEY;


        Map<String, Object> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.error("error - {}", errors);

        return ApiResponse.build()
                .withSuccess(false)
                .withHttpStatus(messageCode.getStatusCode())
                .withCode(messageCode.getCode())
                .withErrors(errors)
                .toEntity();
    }

}
