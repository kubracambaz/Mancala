package com.bol.mancalagame.rules;

import com.bol.mancalagame.domain.dto.GameResponseDto;
import com.bol.mancalagame.domain.dto.PlayRequestDto;
import com.bol.mancalagame.domain.dto.PlayResponseDto;
import com.bol.mancalagame.enums.PlayerType;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(name = "Treasury pit rule", description = "Sowing the last stone to the treasury pit determine the player's turn.")
public class TreasuryPitRule {
    @Condition
    public boolean when(@Fact("playRequest") PlayRequestDto playRequest, @Fact("playResponse") PlayResponseDto playResponse) {
        int index = playResponse.getLastIndex();
        if (playRequest.getPlayerTurn().equals(PlayerType.FIRST) && index == playRequest.getFirstPlayer().getLargePit()) {
            return false;
        } else if (playRequest.getPlayerTurn().equals(PlayerType.SECOND) && index == playRequest.getSecondPlayer().getLargePit()) {
            return false;
        }
        return true;
    }

    @Action
    public void then(@Fact("playResponse") PlayResponseDto playResponse) throws Exception {
        if (playResponse.getPlayerTurn().equals(PlayerType.FIRST)) {
            playResponse.setPlayerTurn(PlayerType.SECOND);
        } else {
            playResponse.setPlayerTurn(PlayerType.FIRST);
        }
    }
}
