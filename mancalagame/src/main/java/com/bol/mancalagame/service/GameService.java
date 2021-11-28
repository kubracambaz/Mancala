package com.bol.mancalagame.service;

import com.bol.mancalagame.domain.dto.*;

public interface GameService {
    GameResponseDto createGame(GameRequestDto requestDto);
    PlayResponseDto playGame(PlayRequestDto playRequestDto) throws Exception;
}
