package com.ragnar.main.Util.Constants;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseCode {
    public static String IsSuccess = String.valueOf(HttpServletResponse.SC_OK);
    public static String IsFailed = String.valueOf(HttpServletResponse.SC_BAD_REQUEST);

    public static String IsCreated = String.valueOf(HttpServletResponse.SC_CREATED);
    public static String IsError = String.valueOf(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    public static String HasNoContent = String.valueOf(HttpServletResponse.SC_NO_CONTENT);
    public static String IsNotFound = String.valueOf(HttpServletResponse.SC_NOT_FOUND);
    public static String IsUnauthorized = String.valueOf(HttpServletResponse.SC_UNAUTHORIZED);
}
