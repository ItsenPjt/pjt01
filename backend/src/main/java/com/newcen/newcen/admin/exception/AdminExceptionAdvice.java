package com.newcen.newcen.admin.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.BindException;

@RestControllerAdvice(basePackages = "com.newcen.newcen.admin")
public class AdminExceptionAdvice extends DefaultHandlerExceptionResolver {

    @ExceptionHandler({HttpServerErrorException.InternalServerError.class})
    public ResponseEntity<?> internalServerErrorHandler(Exception e) {
        return ResponseEntity
                .status(AdminExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(AdminExceptionEntity.builder()
                        .errorCode(AdminExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .errorMessage(AdminExceptionEnum.INTERNAL_SERVER_ERROR.getMessage())
                        .build()
                );
    }

    @ExceptionHandler({
            EmptyResultDataAccessException.class,
            MissingPathVariableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            HttpClientErrorException.BadRequest.class,
            InvalidFormatException.class,
            DuplicateKeyException.class,
            IllegalArgumentException.class,
            DataIntegrityViolationException.class,
            HttpMessageNotReadableException.class,
            BindException.class
    })
    public ResponseEntity<?> invalidParameterHandler(Exception e) {
        return ResponseEntity.status(AdminExceptionEnum.INVALID_PARAMETER.getStatus())
                .body(AdminExceptionEntity.builder()
                        .errorCode(AdminExceptionEnum.INVALID_PARAMETER.getCode())
                        .errorMessage(AdminExceptionEnum.INVALID_PARAMETER.getMessage())
                        .build()
                );
    }

//    @ExceptionHandler({
//            HttpRequestMethodNotSupportedException.class,
//            MethodNotAllowedException.class
//    })
//    public ResponseEntity<?> methodNotAllowedHandler(Exception e) {
//        return ResponseEntity.status(AdminExceptionEnum.NOT_SUPPORTED_METHOD.getStatus())
//                .body(AdminExceptionEntity.builder()
//                        .errorCode(AdminExceptionEnum.NOT_SUPPORTED_METHOD.getCode())
//                        .errorMessage(AdminExceptionEnum.NOT_SUPPORTED_METHOD.getMessage())
//                        .build()
//                );
//    }


    @ExceptionHandler({AdminCustomException.class})
    public ResponseEntity<?> adminCustomExceptionHandler(AdminCustomException ce) {
        return ResponseEntity
                .status(ce.getFaqExceptionEnum().getStatus())
                .body(AdminExceptionEntity.builder()
                        .errorCode(ce.getFaqExceptionEnum().getCode())
                        .errorMessage(ce.getFaqExceptionEnum().getMessage())
                        .build()
                );
    }


//    @Override
//    protected ModelAndView handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//
//        response.sendError(405, "Method Not Supported");
//
//        return super.handleHttpRequestMethodNotSupported(ex, request, response, handler);
//    }
}
