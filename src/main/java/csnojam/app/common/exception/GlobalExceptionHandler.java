package csnojam.app.common.exception;

import csnojam.app.common.response.ApiResponse;
import csnojam.app.common.response.StatusMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<?>> handleApiException(ApiException e) {
        StatusMessage statusMessage = e.getStatusMessage();
        log.error(String.format("ApiException: %s - %d", statusMessage.getMessage(), statusMessage.getHttpStatus().value()));
        return ApiResponse.withNothing(e.getStatusMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e){
        Map<String, String> errorMessage = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.put("message", error.getDefaultMessage());
        });
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
