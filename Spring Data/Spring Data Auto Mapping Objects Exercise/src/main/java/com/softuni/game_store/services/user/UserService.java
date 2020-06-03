package com.softuni.game_store.services.user;


import com.softuni.game_store.domain.dtos.UserLoginDTO;
import com.softuni.game_store.domain.dtos.UserLogoutDTO;
import com.softuni.game_store.domain.dtos.UserRegisterDTO;

public interface UserService {

    String registerUser(UserRegisterDTO userRegisterDTO);

    String loginUser(UserLoginDTO userLoginDTO);

    String logoutUser(UserLogoutDTO userLogoutDTO);

    boolean isAdmin(String email);
}
