package org.scoreboard.repository;

import java.util.List;

import org.scoreboard.model.Match;

public interface ScoreboardRepository {
    void startMatch(String homeTeam, String awayTeam);
    List<Match> getAllMatches();
    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);
    void finishMatch(String homeTeam, String awayTeam);
}
