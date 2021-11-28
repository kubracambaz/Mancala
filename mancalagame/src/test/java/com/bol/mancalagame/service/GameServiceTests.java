package com.bol.mancalagame.service;

import com.bol.mancalagame.domain.dto.GameRequestDto;
import com.bol.mancalagame.domain.dto.GameResponseDto;
import com.bol.mancalagame.domain.dto.PlayRequestDto;
import com.bol.mancalagame.domain.dto.PlayResponseDto;
import com.bol.mancalagame.domain.entity.GameEntity;
import com.bol.mancalagame.domain.entity.PlayerEntity;
import com.bol.mancalagame.enums.GameStatus;
import com.bol.mancalagame.enums.PlayerType;
import com.bol.mancalagame.util.GameHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTests {
    @InjectMocks
    GameServiceImpl service;
    @Mock
    ModelMapper modelMapper;
    @Mock
    GameHelper gameHelper;

    GameRequestDto createGameRequestDto(){
        GameRequestDto requestDto = new GameRequestDto();
        requestDto.setPitCount(4);
        requestDto.setStoneCount(5);
        return requestDto;
    }
    GameResponseDto createGameResponseDto(){
        GameResponseDto gameResponseDto = new GameResponseDto();
        PlayerEntity firstPlayer = new PlayerEntity();
        firstPlayer.setPlayerType(PlayerType.FIRST);
        firstPlayer.setLargePit(5);
        PlayerEntity secondPlayer = new PlayerEntity();
        firstPlayer.setPlayerType(PlayerType.SECOND);
        firstPlayer.setLargePit(10);
        int pits[] = new int[]{5,5,5,5,0,5,5,5,5,0};
        gameResponseDto.setFirstPlayer(firstPlayer);
        gameResponseDto.setSecondPlayer(secondPlayer);
        gameResponseDto.setStatus(GameStatus.STARTED);
        gameResponseDto.setPlayerTurn(PlayerType.FIRST);
        gameResponseDto.setPits(pits);
        return gameResponseDto;
    }
    GameEntity createGameEntity(){
        GameEntity gameEntity = new GameEntity();
        PlayerEntity firstPlayer = new PlayerEntity();
        firstPlayer.setPlayerType(PlayerType.FIRST);
        firstPlayer.setLargePit(5);
        PlayerEntity secondPlayer = new PlayerEntity();
        firstPlayer.setPlayerType(PlayerType.SECOND);
        firstPlayer.setLargePit(10);
        int pits[] = new int[]{5,5,5,5,0,5,5,5,5,0};
        gameEntity.setFirstPlayer(firstPlayer);
        gameEntity.setSecondPlayer(secondPlayer);
        gameEntity.setStatus(GameStatus.STARTED);
        gameEntity.setPlayerTurn(PlayerType.FIRST);
        gameEntity.setPits(pits);
        return gameEntity;
    }

    public PlayRequestDto createPlayRequest(){
        int samplePits[] = new int[]{0, 0, 0, 0, 30, 0, 0, 5, 5, 10};
        PlayRequestDto playRequestDto = new PlayRequestDto();
        playRequestDto.setPlayerTurn(PlayerType.FIRST);
        playRequestDto.setIndex(0);
        playRequestDto.setPits(samplePits);
        PlayerEntity firstPlayer = new PlayerEntity();
        firstPlayer.setLargePit(4);
        firstPlayer.setPlayerType(PlayerType.FIRST);
        playRequestDto.setFirstPlayer(firstPlayer);
        PlayerEntity secondPlayer = new PlayerEntity();
        secondPlayer.setLargePit(9);
        secondPlayer.setPlayerType(PlayerType.SECOND);
        playRequestDto.setSecondPlayer(secondPlayer);
        return playRequestDto;
    }

    @Test
    public void should_CreateNewGame_WhenGivenPitAndStone(){
        GameResponseDto gameResponseDto = createGameResponseDto();
        GameEntity gameEntity = createGameEntity();
        when(modelMapper.map(gameEntity, GameResponseDto.class)).thenReturn(gameResponseDto);
        GameResponseDto game = service.createGame(createGameRequestDto());
        assertEquals(gameResponseDto, game);

    }

    @Test
    public void should_CreateNewPlayer_WhenGivenPitAndPlayerType(){
        PlayerEntity firstPlayer = new PlayerEntity();
        firstPlayer.setPlayerType(PlayerType.FIRST);
        firstPlayer.setLargePit(4);
        PlayerEntity player = service.createPlayer(PlayerType.FIRST,4);
        assertEquals(firstPlayer.getLargePit(), player.getLargePit());

    }

    @Test
    public void should_FinishGame_WhenAllPitsEmptyByPlayerType(){
        PlayRequestDto playRequestDto = createPlayRequest();
        PlayResponseDto playResponseDto = new PlayResponseDto();
        int samplePits[] = new int[]{0, 0, 0, 0, 30, 0, 0, 5, 5, 10};
        playResponseDto.setPits(samplePits);
        playResponseDto.setPlayerTurn(PlayerType.FIRST);
        when(gameHelper.isThereEmptyPit(playResponseDto)).thenReturn(true);
        when(gameHelper.getMinIndex(any(),any())).thenReturn(0);
        when(gameHelper.getMaxIndex(any(),any())).thenReturn(3);
        Mockito.when(gameHelper.collectStones(PlayerType.FIRST,samplePits)).thenReturn(30);
        service.finishGame(playRequestDto,playResponseDto);
        assertEquals(GameStatus.FINISHED, playResponseDto.getStatus());
    }
}
