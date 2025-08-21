package com.restaurants.modules.restaurant.common.exception;


import com.restaurants.api.exception.RestaurantErrorCodeEnum;
import com.restaurants.api.exception.RestaurantException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception exception) {

        log.error("handleException -> ", exception);
        ErrorResponse errorResponse = new ErrorResponse(
                RestaurantErrorCodeEnum.INTERNAL_SERVER_ERROR.description(),
                RestaurantErrorCodeEnum.INTERNAL_SERVER_ERROR.errorCode(),
                RestaurantErrorCodeEnum.INTERNAL_SERVER_ERROR.status());

        return ResponseEntity.status(errorResponse.status()).body(errorResponse);

    }

    @ExceptionHandler(RestaurantException.class)
    protected ResponseEntity<ErrorResponse> handleCommunicationException(RestaurantException exception) {

        log.error("handleCommunicationException -> code={}, ", exception.errorCode(), exception);
        ErrorResponse errorResponse = new ErrorResponse(exception.description(), exception.errorCode(), exception.status());

        return ResponseEntity.status(errorResponse.status()).body(errorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        log.error("handleMethodArgumentNotValidException -> ", exception);
        ErrorResponse errorResponse = new ErrorResponse()
                .description(RestaurantErrorCodeEnum.WRONG_OBJECT_PARAMS.description())
                .errorCode(RestaurantErrorCodeEnum.WRONG_OBJECT_PARAMS.errorCode())
                .status(RestaurantErrorCodeEnum.WRONG_OBJECT_PARAMS.status());

        return ResponseEntity.status(errorResponse.status()).body(errorResponse);

    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ErrorResponse> handleValidationException(ValidationException exception) {

        log.error("handleValidationException -> ", exception);
        ErrorResponse errorResponse = new ErrorResponse()
                .description(RestaurantErrorCodeEnum.WRONG_OBJECT_PARAMS.description())
                .errorCode(RestaurantErrorCodeEnum.WRONG_OBJECT_PARAMS.errorCode())
                .status(RestaurantErrorCodeEnum.WRONG_OBJECT_PARAMS.status());

        return ResponseEntity
                .status(errorResponse.status())
                .body(errorResponse);

    }

}