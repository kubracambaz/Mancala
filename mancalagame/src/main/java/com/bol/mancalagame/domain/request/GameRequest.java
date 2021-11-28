package com.bol.mancalagame.domain.request;

import com.bol.mancalagame.controller.validator.annotation.GameValidator;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@GameValidator
public class GameRequest {
    private int pitCount;
    private int stoneCount;
}
