package com.ragnar.main.Util.ExceptionHandlers;

import com.ragnar.main.Util.CommonModels.ApiResponse;
import com.ragnar.main.Util.Constants.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> HandleGeneralException(Exception ex){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.Failed(ex.getMessage(), ResponseCode.IsError));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> HandleBadCredentialsException(BadCredentialsException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.Failed(ex.getMessage(), ResponseCode.IsFailed));
    }

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ApiResponse<Object>> HandleAuthenticationException(AuthenticationException ex){
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ApiResponse.Failed(ex.getMessage(), ResponseCode.IsUnauthorized));
//    }

    // Handler for handling Method arguement (mostly for DTOs); used for LoginDTO Email and phone validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> HandleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder errorMessages = new StringBuilder();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessages
                .append(error.getField())
                .append(": ")
                .append(error.getDefaultMessage())
                .append("; ");
        }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.Failed(errorMessages.toString().trim(), ResponseCode.IsFailed));
    }
}
