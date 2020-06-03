package com.softuni.game_store.domain.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String email, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
    }

    @NotNull(message = "Email password cannot be null!")
    @Pattern(
            regexp = "[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-z]{2,4}",
            message = "Invalid email!"
    )
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull(message = "Password password cannot be null!")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*[&%$]).{6,}$",
            message = "Password must contain at least 1 uppercase, 1 lowercase letter and 1 digit!"
    )
    @Size(min = 6, message = "Password must be at least 6 symbols long!")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "Confirmed password cannot be null!")
    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
