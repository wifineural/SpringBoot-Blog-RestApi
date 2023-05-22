package org.saini.blogrestapi.exception;

import org.saini.blogrestapi.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest){

        ErrorDetails errorDetails=new ErrorDetails(new Date(),resourceNotFoundException.getMessage(),webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetails> handleBlogApiException(BlogApiException resourceNotFoundException, WebRequest webRequest){

        ErrorDetails errorDetails=new ErrorDetails(new Date(),resourceNotFoundException.getMessage(),webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException exception, WebRequest webRequest){

        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlogbleException(Exception resourceNotFoundException, WebRequest webRequest){

        ErrorDetails errorDetails=new ErrorDetails(new Date(),resourceNotFoundException.getMessage(),webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgNotValidException(MethodArgumentNotValidException exception,WebRequest webRequest){
        Map<String,String> errors= new HashMap<>();

      List<ObjectError> errorList= exception.getBindingResult().getAllErrors();
        errorList.forEach((error) ->{
           // error.
/*            String fieldName= error.getCode();
            String fieldValue=error.getDefaultMessage();

            errors.put(fieldName,fieldValue );*/
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return  new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }



}
