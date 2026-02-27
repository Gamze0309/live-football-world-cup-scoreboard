package org.scoreboard.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.scoreboard.model.Match;

@DisplayName("ScoreBoardService")
public class ScoreboardServiceTest {

    private ScoreboardService scoreBoardService;

    @BeforeEach
    void setUp() {
        scoreBoardService = new ScoreboardService();
    }

    @Test
    @DisplayName("should instantiate scoreboard service")
    void shouldInstantiateScoreBoardService() {
        assertNotNull(scoreBoardService, "ScoreBoardService instance should have been created");
    }

    @Test
    @DisplayName("should return empty matches list initially")
    void shouldReturnEmptyMatchesListInitially() {
        List<Match> matches = scoreBoardService.getAllMatches();
        assertEquals(0, matches.size(), "Initial matches list should be empty");
    }

    @Test
    @DisplayName("should add match when startMatch called")
    void shouldAddMatchWhenStartMatchCalled() {
        scoreBoardService.startMatch("Brazil", "Argentina");

        List<Match> matches = scoreBoardService.getAllMatches();
        assertEquals(1, matches.size(), "Matches list should contain one match after addition");
    }

    @Test
    @DisplayName("should start match with correct team names")
    void shouldStartMatchWithCorrectTeamNames() {
        scoreBoardService.startMatch("Brazil", "Argentina");

        List<Match> matches = scoreBoardService.getAllMatches();
        Match match = matches.get(0);

        assertEquals("Brazil", match.getHomeTeam(), "Home team name should be 'Brazil'");
        assertEquals("Argentina", match.getAwayTeam(), "Away team name should be 'Argentina'");
    }

    @Test
    @DisplayName("should reject when home team already has an active match")
    void shouldRejectWhenHomeTeamAlreadyActive() {
        scoreBoardService.startMatch("Brazil", "Argentina");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> scoreBoardService.startMatch("Brazil", "Canada"));
        assertTrue(exception.getMessage().contains("already has an active match"));
    }

    @Test
    @DisplayName("should reject when away team already has an active match")
    void shouldRejectWhenAwayTeamAlreadyActive() {
        scoreBoardService.startMatch("Turkey", "Canada");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> scoreBoardService.startMatch("Brazil", "Canada"));
        assertTrue(exception.getMessage().contains("already has an active match"));
    }

    @Test
    @DisplayName("should reject duplicate match")
    void shouldRejectDuplicateMatch() {
        scoreBoardService.startMatch("Brazil", "Canada");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> scoreBoardService.startMatch("Brazil", "Canada"));
        assertTrue(exception.getMessage().contains("already has an active match"));
    }
}
