package com.example.library.management.dtos.responses;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private int statusCode;
    private String message;
    private T data;
}