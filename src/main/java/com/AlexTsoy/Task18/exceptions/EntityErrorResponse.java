package com.AlexTsoy.Task18.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityErrorResponse {
    private String message;
    private long timestamp;

    public EntityErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
