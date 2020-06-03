package com.softuni.game_store.domain.dtos;

public class UserLogoutDTO {
    private String email;

    public UserLogoutDTO(){

    }

    public UserLogoutDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}