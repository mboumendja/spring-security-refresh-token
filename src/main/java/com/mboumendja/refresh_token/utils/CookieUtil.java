package com.mboumendja.refresh_token.utils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void addTokenCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        addCookie(response, "accessToken", accessToken, 300);
        addCookie(response, "refreshToken", refreshToken, 864000);
    }

    public static void addAccessTokenCookie(HttpServletResponse response, String accessToken) {
        addCookie(response, "accessToken", accessToken, 300);
    }

    private static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofSeconds(maxAge))
            .sameSite("Strict")
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        return Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
            .filter(c -> name.equals(c.getName()))
            .map(Cookie::getValue)
            .findFirst()
            .orElse(null);
    }
}
