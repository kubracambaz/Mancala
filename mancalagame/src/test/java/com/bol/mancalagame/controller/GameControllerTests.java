package com.bol.mancalagame.controller;

import com.bol.mancalagame.domain.dto.GameResponseDto;
import com.bol.mancalagame.domain.dto.PlayResponseDto;
import com.bol.mancalagame.domain.entity.PlayerEntity;
import com.bol.mancalagame.domain.request.GameRequest;
import com.bol.mancalagame.domain.request.PlayRequest;
import com.bol.mancalagame.domain.response.GameResponse;
import com.bol.mancalagame.domain.response.PlayResponse;
import com.bol.mancalagame.enums.GameStatus;
import com.bol.mancalagame.enums.PlayerType;
import com.bol.mancalagame.service.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GameControllerTests {

    @InjectMocks
    GameController gameController;
    @Mock
    ModelMapper mapper;
    @Mock
    GameService gameService;

    public GameResponse createGameResponse(){
        GameResponse gameResponse = new GameResponse();
        PlayerEntity firstPlayer = new PlayerEntity();
        firstPlayer.setPlayerType(PlayerType.FIRST);
        firstPlayer.setLargePit(5);
        PlayerEntity secondPlayer = new PlayerEntity();
        firstPlayer.setPlayerType(PlayerType.SECOND);
        firstPlayer.setLargePit(10);
        int pits[] = new int[]{5,5,5,5,0,5,5,5,5,0};
        gameResponse.setFirstPlayer(firstPlayer);
        gameResponse.setSecondPlayer(secondPlayer);
        gameResponse.setStatus(GameStatus.STARTED);
        gameResponse.setPlayerTurn(PlayerType.FIRST);
        gameResponse.setPits(pits);
        return gameResponse;
    }

    public GameResponseDto createGameResponseDto(){
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

    GameRequest createGameRequest(){
        GameRequest request = new GameRequest();
        request.setPitCount(4);
        request.setStoneCount(5);
        return request;
    }
    public PlayResponse createPlayResponse(){
        PlayResponse playResponse = new PlayResponse();
        int pits[] = new int[]{0,6,6,6,1,6,5,5,5,0};
        playResponse.setPlayerTurn(PlayerType.FIRST);
        playResponse.setPits(pits);
        playResponse.setStatus(GameStatus.PROCESSING);
        playResponse.setLastIndex(5);
        playResponse.setWinnerPlayer(null);
        return playResponse;
    }

    public PlayResponseDto createPlayResponseDto(){
        PlayResponseDto playResponseDto = new PlayResponseDto();
        int pits[] = new int[]{0,6,6,6,1,6,5,5,5,0};
        playResponseDto.setPlayerTurn(PlayerType.FIRST);
        playResponseDto.setPits(pits);
        playResponseDto.setStatus(GameStatus.PROCESSING);
        playResponseDto.setLastIndex(5);
        playResponseDto.setWinnerPlayer(null);
        return playResponseDto;
    }

    public PlayRequest createPlayRequest(){
        PlayRequest playRequest = new PlayRequest();
        PlayerEntity firstPlayer = new PlayerEntity();
        firstPlayer.setPlayerType(PlayerType.FIRST);
        firstPlayer.setLargePit(4);
        PlayerEntity secondPlayer = new PlayerEntity();
        firstPlayer.setPlayerType(PlayerType.SECOND);
        firstPlayer.setLargePit(9);
        int pits[] = new int[]{5,5,5,5,0,5,5,5,5,0};
        playRequest.setFirstPlayer(firstPlayer);
        playRequest.setPits(pits);
        playRequest.setSecondPlayer(secondPlayer);
        playRequest.setPlayerTurn(PlayerType.FIRST);
        playRequest.setStatus(GameStatus.STARTED);
        playRequest.setIndex(0);
        return playRequest;
    }

    @Test
    public void should_CreateNewGame_WhenGivenPitAndStoneCount(){
        GameResponse gameResponse = createGameResponse();
        GameResponseDto gameResponseDto = createGameResponseDto();
        when(gameService.createGame(any())).thenReturn(gameResponseDto);
        when(mapper.map(gameResponseDto, GameResponse.class)).thenReturn(gameResponse);
        ResponseEntity<GameResponse> response = gameController.createGame(createGameRequest());
        assertEquals(gameResponse, response.getBody());

    }

    @Test
    public void should_MoveStones_WhenGivenPitIndex(){
        ResponseEntity<PlayResponse> response = null;
        PlayResponse playResponse = createPlayResponse();
        PlayResponseDto playResponseDto = createPlayResponseDto();
        try {
            when(gameService.playGame(any())).thenReturn(playResponseDto);
            when(mapper.map(playResponseDto, PlayResponse.class)).thenReturn(playResponse);
            response = gameController.playGame(createPlayRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(playResponse, response.getBody());

    }
}
