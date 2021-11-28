package com.bol.mancalagame.controller.validator;

import com.bol.mancalagame.controller.validator.annotation.PlayValidator;
import com.bol.mancalagame.domain.request.PlayRequest;
import com.bol.mancalagame.enums.GameStatus;
import com.bol.mancalagame.exceptions.ErrorCode;
import com.bol.mancalagame.exceptions.GameBusinessException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PlayRequestValidator implements ConstraintValidator<PlayValidator, PlayRequest> {

    @Override
    public boolean isValid(PlayRequest playRequest, ConstraintValidatorContext constraintValidatorContext) {
        if(playRequest.getStatus().equals(GameStatus.FINISHED)){
            throw new GameBusinessException(ErrorCode.GAME_STATUS_EXCEPTION);
        }
        return true;
    }
}
