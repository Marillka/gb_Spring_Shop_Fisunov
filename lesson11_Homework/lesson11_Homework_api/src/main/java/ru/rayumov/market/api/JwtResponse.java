package ru.rayumov.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class JwtResponse {

    @Schema(description = "JwtToken", required = true, example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib2IiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiZXhwIjoxNjcwMjYzNTQ1LCJpYXQiOjE2NzAyNTk5NDV9.SvUf7_KOnmskdAHwY3KyfhOgbA4xBouAH3DmZO-9hXE"
    )
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JwtResponse() {
    }

    public JwtResponse(String token) {
        this.token = token;
    }
}
