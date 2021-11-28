package com.bol.mancalagame.controller.validator;

import com.bol.mancalagame.domain.request.GameRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import javax.validation.ConstraintValidatorContext;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class GameRequestValidatorTests {
    @InjectMocks
    GameRequestValidator validator;
    @Mock
    ConstraintValidatorContext context;

    GameRequest createGameRequest() {
        GameRequest request = new GameRequest();
        request.setPitCount(4);
        request.setStoneCount(5);
        return request;
    }

    @Test
    public void should_PitAndStoneCountGreaterThanZero() {
        boolean isValid = validator.isValid(createGameRequest(), context);
        assertTrue(isValid);
    }

}
