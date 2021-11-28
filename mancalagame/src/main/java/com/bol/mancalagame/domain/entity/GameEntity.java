package com.bol.mancalagame.domain.entity;

import com.bol.mancalagame.enums.GameStatus;
import com.bol.mancalagame.enums.PlayerType;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameEntity {
    private PlayerEntity firstPlayer;
    private PlayerEntity secondPlayer;
    private PlayerType playerTurn;
    private GameStatus status;
    private int[] pits;

}
