package com.bol.mancalagame.rules;

import com.bol.mancalagame.constants.GameConstants;
import com.bol.mancalagame.domain.dto.PlayRequestDto;
import com.bol.mancalagame.domain.dto.PlayResponseDto;
import com.bol.mancalagame.enums.PlayerType;
import com.bol.mancalagame.util.GameHelper;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.springframework.beans.factory.annotation.Autowired;

@Rule(name = "Last Stone Empty Pit Rule", description = "When the last stone lands in an own empty pit, the player captures his own stone and all stones in the opposite pit (the other player’s pit) and puts them in his own (big or little?) pit.")
public class LastStoneEmptyPitRule {
    private final GameHelper gameHelper = GameHelper.getInstance();

    @Condition
    public boolean when(@Fact("playResponse") PlayResponseDto playResponse) {
        int index = playResponse.getLastIndex();
        int pitCountPerPlayer = (playResponse.getPits().length - GameConstants.NUMBER_TWO)/GameConstants.NUMBER_TWO;
        int firstLargePit = gameHelper.findLargePitByPlayer(PlayerType.FIRST, pitCountPerPlayer);
        int secondLargePit = gameHelper.findLargePitByPlayer(PlayerType.SECOND, pitCountPerPlayer);
        int pits[] = playResponse.getPits();
        int oppositePit = gameHelper.getOpponentPitIndex(pits,index,playResponse.getPlayerTurn());
        if(playResponse.getPits()[index]==1 && index!=firstLargePit && index != secondLargePit && pits[oppositePit] != 0){
            if((playResponse.getPlayerTurn().equals(PlayerType.SECOND) && index>firstLargePit) || (playResponse.getPlayerTurn().equals(PlayerType.FIRST) && index<firstLargePit)) {
                return true;
            }

        }
        return false;
    }

    @Action
    public void then(@Fact("playResponse") PlayResponseDto playResponse) throws Exception {
        int largePitIndex;
        int pitCountPerPlayer = (playResponse.getPits().length - GameConstants.NUMBER_TWO)/GameConstants.NUMBER_TWO;
        if(playResponse.getPlayerTurn().equals(PlayerType.FIRST)){
            largePitIndex = gameHelper.findLargePitByPlayer(PlayerType.FIRST,pitCountPerPlayer);
        }else {
            largePitIndex = gameHelper.findLargePitByPlayer(PlayerType.SECOND,pitCountPerPlayer);
        }
        playResponse.getPits()[largePitIndex] =playResponse.getPits()[largePitIndex] + gameHelper.captureOpponentStones(playResponse) + playResponse.getPits()[playResponse.getLastIndex()];
        playResponse.getPits()[playResponse.getLastIndex()] = GameConstants.NUMBER_ZERO;
    }
}
