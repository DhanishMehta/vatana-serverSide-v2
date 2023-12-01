package com.capstone.grocery.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
    private int statusCode;
    private boolean success;
    private String message;
    private Pagination pagination;
    private T data;
}