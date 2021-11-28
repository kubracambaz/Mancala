package com.bol.mancalagame.controller.validator;

import com.bol.mancalagame.constants.GameConstants;
import com.bol.mancalagame.controller.validator.annotation.GameValidator;
import com.bol.mancalagame.exceptions.ErrorCode;
import com.bol.mancalagame.exceptions.GameBusinessException;
import com.bol.mancalagame.domain.request.GameRequest;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class GameRequestValidator implements ConstraintValidator<GameValidator, GameRequest> {
    @Override
    public void initialize(GameValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(GameRequest gameRequest, ConstraintValidatorContext constraintValidatorContext) {
        setDefaultPit(gameRequest);
        setDefaulStone(gameRequest);
        if (gameRequest.getPitCount() > GameConstants.MAX_PIT_AND_STONE_COUNT || gameRequest.getStoneCount() > GameConstants.MAX_PIT_AND_STONE_COUNT) {
            throw new GameBusinessException(ErrorCode.PIT_AND_STONE_EXCEPTION);
        }
        return true;
    }

    public void setDefaultPit(GameRequest gameRequest) {
        if (gameRequest.getPitCount() <= GameConstants.NUMBER_ZERO) {
            gameRequest.setPitCount(GameConstants.DEFAULT_PIT_AND_STONE_COUNT);
        }
    }

    public void setDefaulStone(GameRequest gameRequest) {
        if (gameRequest.getStoneCount() <= GameConstants.NUMBER_ZERO) {
            gameRequest.setStoneCount(GameConstants.DEFAULT_PIT_AND_STONE_COUNT);
        }
    }

}
