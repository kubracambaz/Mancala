package com.bol.mancalagame.controller;

import com.bol.mancalagame.domain.dto.*;
import com.bol.mancalagame.domain.request.GameRequest;
import com.bol.mancalagame.domain.request.PlayRequest;
import com.bol.mancalagame.domain.response.GameResponse;
import com.bol.mancalagame.domain.response.PlayResponse;
import com.bol.mancalagame.service.GameService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/mancala")
public class GameController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GameService service;

    @ApiOperation(value = "Create new game", nickname = "createGame", notes = "Create new game with pit and stone count by customer request ")
    @PostMapping("/games")
    public ResponseEntity<GameResponse> createGame(@RequestBody @Valid GameRequest request) {
        GameRequestDto requestDto = modelMapper.map(request, GameRequestDto.class);
        GameResponseDto responseDto = service.createGame(requestDto);
        GameResponse gameResponse = modelMapper.map(responseDto, GameResponse.class);
        ResponseEntity<GameResponse> response = new ResponseEntity<GameResponse>(gameResponse, HttpStatus.CREATED);
        return response;
    }

    @ApiOperation(value = "Play game", nickname = "playGame", notes = "Play game by pit index ")
    @PutMapping("/plays")
    public ResponseEntity<PlayResponse> playGame(@RequestBody @Valid PlayRequest playRequest) throws Exception {
        PlayRequestDto playRequestDto = modelMapper.map(playRequest,PlayRequestDto.class);
        PlayResponseDto playResponseDto = service.playGame(playRequestDto);
        PlayResponse playResponse = modelMapper.map(playResponseDto,PlayResponse.class);
        ResponseEntity<PlayResponse> response = new ResponseEntity<PlayResponse>(playResponse, HttpStatus.OK);
        return response;
    }
}
