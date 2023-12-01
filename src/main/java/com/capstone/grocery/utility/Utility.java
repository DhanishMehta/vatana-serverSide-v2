package com.capstone.grocery.utility;

import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.response.Pagination;

public class Utility {
    public static <T> CommonResponse<T> getCommonResponse(int statusCode, boolean success, String message,
            Pagination pagination, T data) {
        return new CommonResponse<>(statusCode, success, message, pagination, data);
    }
}
