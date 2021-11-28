package com.bol.mancalagame.domain.request;

import com.bol.mancalagame.controller.validator.annotation.PlayValidator;
import com.bol.mancalagame.domain.entity.PlayerEntity;
import com.bol.mancalagame.enums.GameStatus;
import com.bol.mancalagame.enums.PlayerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PlayValidator
public class PlayRequest {
    private PlayerEntity firstPlayer;
    private PlayerEntity secondPlayer;
    private PlayerType playerTurn;
    private GameStatus status;
    private int index;
    private int[] pits;
}
