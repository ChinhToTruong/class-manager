package com.zev.studentmanager.exception;

import com.zev.studentmanager.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<?> handlingRuntimeException(Exception exception) {
        log.error("error - {}", exception.getMessage());

        return ApiResponse.build()
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withCode(exception.hashCode())
                .withMessage(exception.getMessage())
                .toEntity();
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<?> handlingAppException(AppException exception) {
        log.error("error - {}", exception.getMessage());

        ErrorCode errorCode = exception.getErrorCode();
        return ApiResponse.build()
                .withHttpStatus((HttpStatus) errorCode.getStatusCode())
                .withCode(errorCode.getCode())
                .withMessage(errorCode.getMessage())
                .toEntity();
    }

    @ExceptionHandler(value = ResourceNotFound.class)
    ResponseEntity<?> handlingResourceException(ResourceNotFound exception) {
        log.error("error - {}", exception.getMessage());

        ErrorCode errorCode = exception.getErrorCode();
        return ApiResponse.build()
                .withHttpStatus((HttpStatus) errorCode.getStatusCode())
                .withCode(errorCode.getCode())
                .withMessage(errorCode.getMessage())
                .toEntity();
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<?> handlingAccessDeniedException(AccessDeniedException exception) {
        log.error("error - {}", exception.getMessage());

        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ApiResponse.build()
                .withHttpStatus((HttpStatus) errorCode.getStatusCode())
                .withCode(errorCode.getCode())
                .withMessage(errorCode.getMessage())
                .toEntity();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<?> handlingValidation(MethodArgumentNotValidException exception) {
        ErrorCode errorCode = ErrorCode.INVALID_KEY;


        Map<String, Object> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.error("error - {}", errors);

        return ApiResponse.build()
                .withHttpStatus((HttpStatus) errorCode.getStatusCode())
                .withCode(errorCode.getCode())
                .withErrors(errors)
                .toEntity();
    }
}
