package ru.rayumov.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class JwtRequest {
    @Schema(description = "Логин пользователя", required = true, example = "bob")
    private String username;
    @Schema(description = "Пароль пользователя", required = true, example = "zZ123456")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
