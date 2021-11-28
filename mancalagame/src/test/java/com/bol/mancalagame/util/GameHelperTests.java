package com.bol.mancalagame.util;

import com.bol.mancalagame.domain.dto.PlayRequestDto;
import com.bol.mancalagame.domain.dto.PlayResponseDto;
import com.bol.mancalagame.enums.PlayerType;
import com.bol.mancalagame.exceptions.ErrorCode;
import com.bol.mancalagame.exceptions.GameBusinessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class GameHelperTests {
    @InjectMocks
    GameHelper helper;

    public PlayRequestDto createPlayRequest(){
        int samplePits[] = new int[]{5, 5, 5, 5, 0, 5, 5, 5, 5, 0};
        PlayRequestDto playRequestDto = new PlayRequestDto();
        playRequestDto.setPlayerTurn(PlayerType.FIRST);
        playRequestDto.setIndex(0);
        playRequestDto.setPits(samplePits);
        return playRequestDto;
    }

    public PlayResponseDto createPlayResponse(){
        PlayResponseDto playResponseDto = new PlayResponseDto();
        return playResponseDto;
    }

    @Test
    public void should_FindLargePit_WhenGivenPlayerTypeAndPit() {
        int largePit = helper.findLargePitByPlayer(PlayerType.FIRST, 4);
        assertEquals(4, largePit);
    }

    @Test
    public void should_FillPits_WhenGivenStoneCount() {
        int samplePits[] = new int[]{5, 5, 5, 5, 0, 5, 5, 5, 5, 0};
        int pits[] = helper.fillPitsWithStone(4, 5);
        assertEquals(samplePits[1], pits[1]);
    }

    @Test
    public void should_BeValidIndex_WhenPlay() {
       // int samplePits[] = new int[]{5, 5, 5, 5, 0, 5, 5, 5, 5, 0};
        PlayRequestDto playRequestDto = createPlayRequest();
        playRequestDto.setIndex(5);
        Exception exception = assertThrows(GameBusinessException.class, () -> {
            helper.validateIndex(playRequestDto);
        });
        String expectedMessage = ErrorCode.MOVE_EXCEPTION.getErrMsgKey();
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void should_ReturnMaxIndex_WhenGivenPlayerType(){
        int samplePits[] = new int[]{5, 5, 5, 5, 0, 5, 5, 5, 5, 0};
        int maxIndex = helper.getMaxIndex(samplePits,PlayerType.FIRST);
        assertEquals(3, maxIndex);
    }

    @Test
    public void should_ReturnMinIndex_WhenGivenPlayerType(){
        int samplePits[] = new int[]{5, 5, 5, 5, 0, 5, 5, 5, 5, 0};
        int minIndex = helper.getMinIndex(samplePits,PlayerType.FIRST);
        assertEquals(0, minIndex);
    }

    @Test
    public void should_IterateStones_WhenGivenIndex(){
        PlayRequestDto playRequestDto = createPlayRequest();
        PlayResponseDto playResponseDto = new PlayResponseDto();
        helper.iterateStones(playRequestDto,playResponseDto);
        assertEquals(5, playResponseDto.getLastIndex());
    }

    @Test
    public void should_CaptureOpponentStones_WhenGivenOppositeIndex(){
        PlayResponseDto playResponseDto = new PlayResponseDto();
        int samplePits[] = new int[]{5, 5, 5, 5, 0, 5, 5, 5, 5, 0};
        playResponseDto.setLastIndex(2);
        playResponseDto.setPits(samplePits);
        int opponentStones = helper.captureOpponentStones(playResponseDto);
        assertEquals(5, opponentStones);
    }
}
