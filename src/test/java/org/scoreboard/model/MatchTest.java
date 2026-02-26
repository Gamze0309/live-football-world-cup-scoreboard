package org.scoreboard.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class MatchTest {

    @Test
    void shouldInstantiateMatch() {
        Match match = new Match("Mexico", "Canada");
        assertNotNull(match, "Match instance should have been created");
    }

    @Test
    void shouldReturnTeamNamesFromGetters() {
        Match match = new Match("Mexico", "Canada");
        assertEquals("Mexico", match.getHomeTeam());
        assertEquals("Canada", match.getAwayTeam());
    }
    
    @Test
    void shouldInitializeScoresToZero() {
        Match match = new Match("Mexico", "Canada");

        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void shouldRejectInvalidHomeTeamName(String invalidHome) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Match(invalidHome, "Turkey"));
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void shouldRejectInvalidAwayTeamName(String invalidAway) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Match("Germany", invalidAway));
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldNormalizeHomeTeamNameByTrimmingWhitespace() {
        Match match = new Match("Mexico ", "Canada");
        assertEquals("Mexico", match.getHomeTeam());
    }

    @Test
    void shouldNormalizeAwayTeamNameByTrimmingWhitespace() {
        Match match = new Match("Mexico", "Canada ");
        assertEquals("Canada", match.getAwayTeam());
    }
}
