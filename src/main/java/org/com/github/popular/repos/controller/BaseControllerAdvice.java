package org.com.github.popular.repos.controller;

import com.google.common.cache.CacheLoader;
import org.com.github.popular.repos.exception.GitHubApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class BaseControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(BaseControllerAdvice.class);

    @ExceptionHandler(GitHubApiException.class)
    public ResponseEntity<Object> handleGitHubApiException(GitHubApiException ex) {
        logger.error("GitHub API exception occurred", ex);
        return new ResponseEntity<>(createErrorBody(ex.getMessage(), ex.getHttpStatus()), ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        logger.error("Unexpected error occurred", ex);
        return new ResponseEntity<>(createErrorBody("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CacheLoader.InvalidCacheLoadException.class)
    public ResponseEntity<Object> handleCacheException(CacheLoader.InvalidCacheLoadException e) {
        // Log the exception and return a generic error response or a fallback value
        logger.error("Cache operation failed: {}", e.getMessage());
        return new ResponseEntity<>(createErrorBody("Cache error occurred. Fallback response provided.", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private Map<String, Object> createErrorBody(String message, HttpStatus status) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", LocalDateTime.now());
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
        errorAttributes.put("message", message);
        return errorAttributes;
    }
}

