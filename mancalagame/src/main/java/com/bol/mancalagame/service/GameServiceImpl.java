package com.bol.mancalagame.service;

import com.bol.mancalagame.config.RuleManager;
import com.bol.mancalagame.domain.dto.*;
import com.bol.mancalagame.domain.entity.GameEntity;
import com.bol.mancalagame.domain.entity.PlayerEntity;
import com.bol.mancalagame.enums.GameStatus;
import com.bol.mancalagame.enums.PlayerType;
import com.bol.mancalagame.exceptions.GameBusinessException;
import com.bol.mancalagame.util.GameHelper;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {
    private final GameHelper gameHelper = GameHelper.getInstance();
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RuleManager ruleManager;

    @Override
    public GameResponseDto createGame(GameRequestDto requestDto) {
        GameEntity gameEntity = GameEntity.builder()
                .firstPlayer(createPlayer(PlayerType.FIRST, requestDto.getPitCount()))
                .secondPlayer(createPlayer(PlayerType.SECOND, requestDto.getPitCount()))
                .pits(gameHelper.fillPitsWithStone(requestDto.getPitCount(), requestDto.getStoneCount()))
                .playerTurn(PlayerType.FIRST)
                .status(GameStatus.STARTED)
                .build();
        return modelMapper.map(gameEntity, GameResponseDto.class);
    }

    public PlayerEntity createPlayer(PlayerType playerType, int pitCount) {
        return PlayerEntity.builder()
                .playerType(playerType)
                .largePit(gameHelper.findLargePitByPlayer(playerType, pitCount))
                .build();
    }

    @Override
    public PlayResponseDto playGame(PlayRequestDto playRequestDto) throws GameBusinessException {
        PlayResponseDto playResponseDto = new PlayResponseDto();
        gameHelper.validateIndex(playRequestDto);
        gameHelper.iterateStones(playRequestDto,playResponseDto);
        runGameRules(playRequestDto, playResponseDto);
        finishGame(playRequestDto,playResponseDto);
        return playResponseDto;
    }

    public void runGameRules(PlayRequestDto playRequestDto, PlayResponseDto playResponseDto){
        Facts facts = new Facts();
        facts.put("playRequest", playRequestDto);
        facts.put("playResponse", playResponseDto);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(ruleManager.rules(), facts);
    }

    public void finishGame(PlayRequestDto playRequestDto, PlayResponseDto playResponseDto){
        if(gameHelper.isThereEmptyPit(playResponseDto)){
            PlayerEntity firstPlayer = playRequestDto.getFirstPlayer();
            PlayerEntity secondPlayer = playRequestDto.getSecondPlayer();
            int firstPlayerTotalStones = playResponseDto.getPits()[firstPlayer.getLargePit()] + gameHelper.collectStones(PlayerType.FIRST,playResponseDto.getPits());
            int secondPlayerTotalStones = playResponseDto.getPits()[secondPlayer.getLargePit()] + gameHelper.collectStones(PlayerType.SECOND,playResponseDto.getPits());
            if(firstPlayerTotalStones > secondPlayerTotalStones){
                playResponseDto.setWinnerPlayer(firstPlayer);
            }
            else if(firstPlayerTotalStones < secondPlayerTotalStones){
                playResponseDto.setWinnerPlayer(secondPlayer);
            }
            playResponseDto.setStatus(GameStatus.FINISHED);
        }else{
            playResponseDto.setStatus(GameStatus.PROCESSING);
        }
    }


}
