package com.bol.mancalagame.util;

import com.bol.mancalagame.constants.GameConstants;
import com.bol.mancalagame.domain.dto.PlayRequestDto;
import com.bol.mancalagame.domain.dto.PlayResponseDto;
import com.bol.mancalagame.enums.PlayerType;
import com.bol.mancalagame.exceptions.ErrorCode;
import com.bol.mancalagame.exceptions.GameBusinessException;
import org.springframework.cache.annotation.Cacheable;

import java.util.stream.IntStream;

public class GameHelper {

    private static class InstanceHolder {
        private static final GameHelper INSTANCE = new GameHelper();

        private InstanceHolder() {

        }
    }

    public static GameHelper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private GameHelper() {
    }

    @Cacheable(cacheNames = "LargePit", key = "#playerType")
    public int findLargePitByPlayer(PlayerType playerType, int pitCount) {
        return playerType.equals(PlayerType.FIRST) ? pitCount : (pitCount * GameConstants.NUMBER_TWO) + GameConstants.NUMBER_ONE;
    }

    public int[] fillPitsWithStone(int pit, int stone) {
        int firstLargePit = findLargePitByPlayer(PlayerType.FIRST, pit);
        int secondLargePit = findLargePitByPlayer(PlayerType.SECOND, pit);
        int totalPitCount = pit * GameConstants.NUMBER_TWO + GameConstants.NUMBER_TWO;
        int[] pits = new int[totalPitCount];
        IntStream.range(GameConstants.NUMBER_ZERO, totalPitCount)
                .filter(index -> (index != firstLargePit && index != secondLargePit))
                .forEach(index -> pits[index] = stone);
        return pits;
    }

    public void validateIndex(PlayRequestDto playRequestDto) throws GameBusinessException {
        int maxIndex = getMaxIndex(playRequestDto.getPits(),playRequestDto.getPlayerTurn());
        int minIndex = getMinIndex(playRequestDto.getPits(),playRequestDto.getPlayerTurn());
        int currentIndex = playRequestDto.getIndex();
        if (currentIndex > maxIndex || currentIndex < minIndex || playRequestDto.getPits()[currentIndex] == GameConstants.NUMBER_ZERO) {
            throw new GameBusinessException(ErrorCode.MOVE_EXCEPTION);
        }
    }

    @Cacheable(cacheNames = "maxIndex", key = "#playerType")
    public int getMaxIndex(int pits[], PlayerType playerType) {
        int pitPerRow= pits.length/GameConstants.NUMBER_TWO;
        return (pitPerRow) + (playerType.ordinal() * pitPerRow) - GameConstants.NUMBER_TWO;
    }

    @Cacheable(cacheNames = "minIndex", key = "#playerType")
    public int getMinIndex(int pits[], PlayerType playerType) {
        int pitPerRow= pits.length/GameConstants.NUMBER_TWO;
        return playerType.ordinal() * pitPerRow;
    }

    public void iterateStones(PlayRequestDto playRequestDto, PlayResponseDto playResponseDto){
        int currentIndex = playRequestDto.getIndex();
        int stoneCount = playRequestDto.getPits()[currentIndex];
        playResponseDto.setPlayerTurn(playRequestDto.getPlayerTurn());
        playResponseDto.setPits(playRequestDto.getPits());
        playResponseDto.getPits()[currentIndex]=GameConstants.NUMBER_ZERO;
        while (stoneCount>GameConstants.NUMBER_ZERO){
            currentIndex++;
            currentIndex=Math.floorMod(currentIndex,playResponseDto.getPits().length);
            playResponseDto.getPits()[currentIndex]++;
            stoneCount--;
        }
        playResponseDto.setLastIndex(currentIndex);
    }

    public boolean isThereEmptyPit(PlayResponseDto playResponseDto) {
        return IntStream
                .rangeClosed(getMinIndex(playResponseDto.getPits(),playResponseDto.getPlayerTurn()), getMaxIndex(playResponseDto.getPits(),playResponseDto.getPlayerTurn()))
                .noneMatch(index -> playResponseDto.getPits()[index] != GameConstants.NUMBER_ZERO);
    }

    public int collectStones(PlayerType playerType,int pits[]) {
        return IntStream
                .rangeClosed(getMinIndex(pits,playerType), getMaxIndex(pits,playerType))
                .map(i -> pits[i]).sum();
    }

    public int getOpponentPitIndex(int pits[],int currentIndex,PlayerType playerType) {

            return  pits.length-GameConstants.NUMBER_TWO - currentIndex;


        }

    public int captureOpponentStones(PlayResponseDto playResponseDto) {
        int oppositeIndex = getOpponentPitIndex(playResponseDto.getPits(),playResponseDto.getLastIndex(),playResponseDto.getPlayerTurn());
        int oppositeStones = playResponseDto.getPits()[oppositeIndex];
        playResponseDto.getPits()[oppositeIndex]= GameConstants.NUMBER_ZERO;
        return oppositeStones;
    }
}
