package com.cromey.identity.webflux.error;

import com.cromey.identity.webflux.controller.ProfileController;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Locale;

@ControllerAdvice(assignableTypes = {ProfileController.class})
public class ProfileControllerExceptionHandler {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Object> badRequestException(final WebExchangeBindException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getStatus().value(),
                e.getStatus().getReasonPhrase().toLowerCase(),
                e.getReason().toLowerCase(),
                e.getFieldError().getField() + " " + e.getFieldError().getDefaultMessage()
                        + " : Custom message = " + (messageSource().getMessage(e.getFieldError().getCode(), null, Locale.ENGLISH))));
    }
}
