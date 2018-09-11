package com.cromey.identity.exception;

import com.cromey.identity.controller.ProfileController;
import com.cromey.identity.error.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Locale;

@ControllerAdvice(assignableTypes = {ProfileController.class})
public class ProfileHandler {

    private static Logger logger = LoggerFactory.getLogger(ProfileHandler.class);

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Object> badRequestException(final WebExchangeBindException e) {
        logger.info("bad request exception");
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getStatus().value(),
                e.getStatus().getReasonPhrase().toLowerCase(),
                e.getReason().toLowerCase(),
                e.getFieldError().getField() + " " + e.getFieldError().getDefaultMessage()
                        + " : Custom message = " + (messageSource().getMessage(e.getFieldError().getCode(), null, Locale.ENGLISH))));
    }
}
