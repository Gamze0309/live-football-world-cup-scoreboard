package org.scoreboard.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.scoreboard.model.Match;

@DisplayName("ScoreBoardService")
public class ScoreboardServiceTest {

    @Test
    @DisplayName("should instantiate scoreboard service")
    void shouldInstantiateScoreBoardService() {
        ScoreboardService scoreBoardService = new ScoreboardService();
        assertNotNull(scoreBoardService, "ScoreBoardService instance should have been created");
    }

    @Test
    @DisplayName("should return empty matches list initially")
    void shouldReturnEmptyMatchesListInitially() {
        ScoreboardService scoreBoardService = new ScoreboardService();
        List<Match> matches = scoreBoardService.getAllMatches();
        assertEquals(0, matches.size(), "Initial matches list should be empty");
    }

    @Test
    @DisplayName("should add match when startMatch called")
    void shouldAddMatchWhenStartMatchCalled() {
        ScoreboardService scoreBoardService = new ScoreboardService();
        scoreBoardService.startMatch("Brazil", "Argentina");

        List<Match> matches = scoreBoardService.getAllMatches();
        assertEquals(1, matches.size(), "Matches list should contain one match after addition");
    }

    @Test
    @DisplayName("should start match with correct team names")
    void shouldStartMatchWithCorrectTeamNames() {
        ScoreboardService scoreBoardService = new ScoreboardService();
        scoreBoardService.startMatch("Brazil", "Argentina");

        List<Match> matches = scoreBoardService.getAllMatches();
        Match match = matches.get(0);

        assertEquals("Brazil", match.getHomeTeam(), "Home team name should be 'Brazil'");
        assertEquals("Argentina", match.getAwayTeam(), "Away team name should be 'Argentina'");
    }
}
