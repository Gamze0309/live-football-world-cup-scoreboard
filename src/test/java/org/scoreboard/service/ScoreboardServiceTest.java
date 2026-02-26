package org.scoreboard.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.scoreboard.model.Match;

public class ScoreboardServiceTest {

    @Test
    void shouldInstantiateScoreBoardService() {
        ScoreboardService scoreBoardService = new ScoreboardService();
        assertNotNull(scoreBoardService, "ScoreBoardService instance should have been created");
    }

    @Test
    void shouldReturnEmptyMatchesListInitially() {
        ScoreboardService scoreBoardService = new ScoreboardService();
        List<Match> matches = scoreBoardService.getAllMatches();
        assertEquals(0, matches.size(), "Initial matches list should be empty");
    }

    @Test
    void shouldAddMatchWhenStartMatchCalled() {
        ScoreboardService scoreBoardService = new ScoreboardService();
        scoreBoardService.startMatch("Brazil", "Argentina");

        List<Match> matches = scoreBoardService.getAllMatches();
        assertEquals(1, matches.size(), "Matches list should contain one match after addition");
    }

    @Test
    void shouldStartMatchWithCorrectTeamNames() {
        ScoreboardService scoreBoardService = new ScoreboardService();
        scoreBoardService.startMatch("Brazil", "Argentina");

        List<Match> matches = scoreBoardService.getAllMatches();
        Match match = matches.get(0);

        assertEquals("Brazil", match.getHomeTeam(), "Home team name should be 'Brazil'");
        assertEquals("Argentina", match.getAwayTeam(), "Away team name should be 'Argentina'");
    }
}
