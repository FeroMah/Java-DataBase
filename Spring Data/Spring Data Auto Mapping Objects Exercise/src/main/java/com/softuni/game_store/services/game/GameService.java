package com.softuni.game_store.services.game;


import com.softuni.game_store.domain.dtos.GameAddDTO;



public interface GameService {

    String addGame(GameAddDTO gameAddDTO);
}
