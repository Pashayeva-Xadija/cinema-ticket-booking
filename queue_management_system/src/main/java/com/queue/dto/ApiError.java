package com.queue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
    private int status;
    private String message;
    public static ApiError of(int s, String m){ return new ApiError(s,m); }
}
