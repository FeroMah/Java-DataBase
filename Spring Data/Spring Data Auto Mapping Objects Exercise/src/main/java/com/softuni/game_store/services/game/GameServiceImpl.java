package com.softuni.game_store.services.game;


import com.softuni.game_store.domain.dtos.GameAddDTO;
import com.softuni.game_store.domain.entities.Game;
import com.softuni.game_store.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private ModelMapper modelMapper;
    private Validator validator;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;

        this.modelMapper = new ModelMapper();
        this.validator = Validation
                .byDefaultProvider()
                .configure()
                .buildValidatorFactory()
                .getValidator();
    }

    @Override
    public String addGame(GameAddDTO gameAddDTO) {
        StringBuilder sb = new StringBuilder();

        if (this.validator.validate(gameAddDTO).size() > 0) {
            for (ConstraintViolation<GameAddDTO> validator : this.validator.validate(gameAddDTO)) {
                sb.append(validator.getMessage()).append(System.lineSeparator());
                return sb.toString();
            }
        } else if (this.gameRepository.count() > 0) {
            Game entity = this.gameRepository.findByTitle(gameAddDTO.getTitle());

            if (entity != null) {
                sb.append("Game already exists in database").append(System.lineSeparator());
                return sb.toString();
            }
        } else {
            Game entity = this.modelMapper.map(gameAddDTO, Game.class);

            this.gameRepository.saveAndFlush(entity);
        }

        sb.append(String.format("Added %s", gameAddDTO.getTitle())).append(System.lineSeparator());

        return sb.toString();
    }
}
