package org.scoreboard.repository;

import java.util.ArrayList;
import java.util.List;

import org.scoreboard.model.Match;

public class InMemoryScoreboardRepository implements ScoreboardRepository {
    private final List<Match> matches = new ArrayList<>();

    @Override
    public List<Match> getAllMatches() {
        return matches;
    }

    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        
        for (Match match : matches) {
            if (homeTeam.equalsIgnoreCase(match.getHomeTeam()) || homeTeam.equalsIgnoreCase(match.getAwayTeam())) {
                throw new IllegalStateException("Team " + homeTeam  + " already has an active match");
            }

             if (awayTeam.equalsIgnoreCase(match.getHomeTeam()) || awayTeam.equalsIgnoreCase(match.getAwayTeam())) {
                throw new IllegalStateException("Team " + awayTeam  + " already has an active match");
            }
        }

        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
    }

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }

        for (int i = 0; i < matches.size(); i++) {
            if (homeTeam.equalsIgnoreCase(matches.get(i).getHomeTeam()) &&
                awayTeam.equalsIgnoreCase(matches.get(i).getAwayTeam())) {
                    matches.set(i, new Match(homeTeam, awayTeam, homeScore, awayScore));
                return;
            }
        }

        throw new IllegalStateException("Match between " + homeTeam + " and " + awayTeam + " not found");
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {

        for (int i = 0; i < matches.size(); i++) {
            if (homeTeam.equalsIgnoreCase(matches.get(i).getHomeTeam()) &&
                awayTeam.equalsIgnoreCase(matches.get(i).getAwayTeam())) {
                    matches.remove(i);
                    return;
            }
        }

        throw new IllegalStateException("Match between " + homeTeam + " and " + awayTeam + " not found");
    }
}
