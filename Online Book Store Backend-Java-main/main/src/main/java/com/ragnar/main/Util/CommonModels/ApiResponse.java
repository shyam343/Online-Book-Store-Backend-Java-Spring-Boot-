package com.ragnar.main.Util.CommonModels;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;


    public static <T> ApiResponse<T> Success(T data, String message, String code) {
        return ApiResponse.<T>builder()
                .data(data)
                .code(code)
                .message(message)
                .success(true)
                .build();
    }

    public static <T> ApiResponse<T> Failed(String message, String code) {
        return ApiResponse.<T>builder()
                .data(null)
                .code(code)
                .message(message)
                .success(false)
                .build();
    }
}
