package com.bol.mancalagame.domain.entity;

import com.bol.mancalagame.enums.PlayerType;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PlayerEntity {
    private PlayerType playerType;
    private int largePit;
}
