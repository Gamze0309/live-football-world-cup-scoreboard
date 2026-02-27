package org.scoreboard.service;

import java.util.List;

import org.scoreboard.model.Match;
import org.scoreboard.repository.ScoreboardRepository;

public class ScoreboardService {
    
    private final ScoreboardRepository scoreboardRepository;

    public ScoreboardService(ScoreboardRepository scoreboardRepository) {
        this.scoreboardRepository = scoreboardRepository;
    }
    
    public List<Match> getAllMatches() {
         return scoreboardRepository.getAllMatches();
    }

    public void startMatch(String homeTeam, String awayTeam) {
        scoreboardRepository.startMatch(homeTeam, awayTeam);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        String normalizedHome = Match.validateAndNormalizeTeamName(homeTeam);
        String normalizedAway = Match.validateAndNormalizeTeamName(awayTeam);

        scoreboardRepository.updateScore(normalizedHome, normalizedAway, homeScore, awayScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) {

        String normalizedHome = Match.validateAndNormalizeTeamName(homeTeam);
        String normalizedAway = Match.validateAndNormalizeTeamName(awayTeam);

        scoreboardRepository.finishMatch(normalizedHome, normalizedAway);
    }
}
