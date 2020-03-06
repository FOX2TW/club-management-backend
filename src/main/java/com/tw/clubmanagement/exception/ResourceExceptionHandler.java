package com.tw.clubmanagement.exception;

import com.tw.clubmanagement.exception.representation.HttpStatusExceptionRepresentation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.UUID;

@ControllerAdvice
@Slf4j
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleResourceException(Exception ex, WebRequest request) {
        if (ex instanceof AbstractHttpStatusException) {
            return toResponseEntity((AbstractHttpStatusException) ex, request);
        } else if (ex instanceof AccessDeniedException) {
            return toResponseEntity(ex, null, HttpStatus.FORBIDDEN, request);
        } else if (ex instanceof ConstraintViolationException) {
            return toResponseEntity(ex, null, HttpStatus.BAD_REQUEST, request);
        } else {
            return toResponseEntity(ex, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleBindingResultException(ex, ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        return handleBindingResultException(ex, ex.getBindingResult(), headers, status, request);
    }

    private ResponseEntity<Object> handleBindingResultException(Exception ex,
                                                                BindingResult bindingResult, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage;
        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().get(0);
            errorMessage = objectError.getDefaultMessage();
        } else {
            errorMessage = ex.getMessage();
        }

        return toResponseEntity(ex, errorMessage, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        return toResponseEntity(ex, headers, status, request);
    }

    private ResponseEntity<Object> toResponseEntity(AbstractHttpStatusException ex,
                                                    WebRequest request) {
        return toResponseEntity(ex, null, ex.getStatus(), request);
    }

    private ResponseEntity<Object> toResponseEntity(Exception ex, HttpHeaders headers,
                                                    HttpStatus status, WebRequest request) {
        return toResponseEntity(ex, ex.getMessage(), headers, status, request);
    }

    private ResponseEntity<Object> toResponseEntity(Exception ex, String message, HttpHeaders headers,
                                                    HttpStatus status, WebRequest request) {

        HttpStatusExceptionRepresentation exception = new HttpStatusExceptionRepresentation();

        exception.setTimestamp(System.currentTimeMillis());
        exception.setStatusCode(status.value());
        exception.setStatusDescription(status.getReasonPhrase());
        exception.setPath(getRequestPath(request));
        exception.setMessage(message);
        exception.setHash(UUID.randomUUID().toString());
        log.error("ResourceExceptionHandler", ex);

        return new ResponseEntity(exception, headers, status);
    }

    /**
     * Extract request URI from WebRequest
     */
    private String getRequestPath(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            return ((ServletWebRequest) request).getRequest().getRequestURI();
        }

        return request.getContextPath();
    }
}
