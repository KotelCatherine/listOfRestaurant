package com.restaurants.api.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(fluent = true)
public class MenuItemException extends Exception{

    private final String errorCode;
    private final String description;
    private final HttpStatus status;

    public MenuItemException(MenuItemErrorCodeEnum code) {
        super(code.description());
        this.errorCode = code.errorCode();
        this.description = code.description();
        this.status = code.status();
    }

}
