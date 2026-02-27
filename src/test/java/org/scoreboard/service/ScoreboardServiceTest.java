package org.scoreboard.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
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

    @Test
    @DisplayName("should detect active teams case-insensitively")
    void shouldDetectActiveTeamsCaseInsensitively() {
        scoreBoardService.startMatch("Turkey", "brazil");

        assertAll(
            () -> assertThrows(IllegalStateException.class,
                () -> scoreBoardService.startMatch("Brazil", "Canada")),
            () -> assertThrows(IllegalStateException.class,
                () -> scoreBoardService.startMatch("Canada", "TURKEY"))
        );
    }

    @Test
    @DisplayName("should update match scores")
    void shouldUpdateMatchScores() {
        scoreBoardService.startMatch("Brazil", "Argentina");
        scoreBoardService.updateScore("Brazil", "Argentina", 2, 3);

        Match match = scoreBoardService.getAllMatches().get(0);
        assertAll(
            () -> assertEquals(2, match.getHomeScore()),
            () -> assertEquals(3, match.getAwayScore())
        );
    }

    @Test
    @DisplayName("should update scores multiple times on same match")
    void shouldUpdateScoresMultipleTimes() {
        scoreBoardService.startMatch("Brazil", "Argentina");
        scoreBoardService.updateScore("Brazil", "Argentina", 1, 0);
        scoreBoardService.updateScore("Brazil", "Argentina", 2, 3);

        Match match = scoreBoardService.getAllMatches().get(0);
        assertAll(
            () -> assertEquals(2, match.getHomeScore()),
            () -> assertEquals(3, match.getAwayScore())
        );
    }

    @Test
    @DisplayName("should update scores ignoring case")
    void shouldUpdateScoresIgnoringCase() {
        scoreBoardService.startMatch("Brazil", "Argentina");
        scoreBoardService.updateScore("brazil", "argentina", 2, 3);

        Match match = scoreBoardService.getAllMatches().get(0);
        assertAll(
            () -> assertEquals(2, match.getHomeScore()),
            () -> assertEquals(3, match.getAwayScore())
        );
    }

    @Test
    @DisplayName("should update scores with trimmed team names")
    void shouldUpdateScoresWithTrimmedTeamNames() {
        scoreBoardService.startMatch("Mexico", "Germany");
        scoreBoardService.updateScore("Mexico ", " Germany", 3, 3);

        assertEquals(3, scoreBoardService.getAllMatches().get(0).getHomeScore());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    @DisplayName("should reject invalid home team name on update score")
    void shouldRejectInvalidHomeTeamNameOnUpdateScore(String invalidHome) {
        scoreBoardService.startMatch("Canada", "Brazil");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.updateScore(invalidHome, "Brazil", 1, 1));
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    @DisplayName("should reject invalid away team name on updateScore")
    void shouldRejectInvalidAwayTeamNameOnUpdateScore(String invalidAway) {
        scoreBoardService.startMatch("Canada", "Brazil");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.updateScore("Canada", invalidAway, 1, 1));
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("should not update non existing match")
    void shouldNotUpdateNonExistingMatch() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> scoreBoardService.updateScore("Canada", "Brazil", 1, 1));
        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    @DisplayName("should not update with negative home score")
    void shouldNotUpdateWithNegativeHomeScore() {
        scoreBoardService.startMatch("Canada", "Brazil");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.updateScore("Canada", "Brazil", -1, 1));
        assertTrue(exception.getMessage().contains("negative"));
    }

    @Test
    @DisplayName("should not update with negative away score")
    void shouldNotUpdateWithNegativeAwayScore() {
        scoreBoardService.startMatch("Canada", "Brazil");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.updateScore("Canada", "Brazil", 1, -1));
        assertTrue(exception.getMessage().contains("negative"));
    }
}
