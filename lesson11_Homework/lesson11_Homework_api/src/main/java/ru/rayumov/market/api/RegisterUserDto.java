package ru.rayumov.market.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class RegisterUserDto {

    @Schema(description = "Логин", required = true, example = "bob")
    private String username;

    @Schema(description = "Пароль", required = true, example = "zZ123456")
    private String password;

    @Schema(description = "Подтверждение пароля", required = true, example = "zZ123456")
    private String confirmPassword;

    @Schema(description = "Почта", required = true, example = "valery@rayumov.ru")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
