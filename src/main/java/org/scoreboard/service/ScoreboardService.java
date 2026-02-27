package org.scoreboard.service;

import java.util.ArrayList;
import java.util.List;

import org.scoreboard.model.Match;

public class ScoreboardService {
    private final List<Match> matches = new ArrayList<>();
    
    public List<Match> getAllMatches() {
        return matches;
    }

    public void startMatch(String homeTeam, String awayTeam) {
        
        for (Match match : matches) {
            if (homeTeam.equals(match.getHomeTeam()) || homeTeam.equals(match.getAwayTeam())) {
                throw new IllegalStateException("Team " + homeTeam  + " already has an active match");
            }

            if (awayTeam.equals(match.getHomeTeam()) || awayTeam.equals(match.getAwayTeam())) {
                throw new IllegalStateException("Team " + awayTeam  + " already has an active match");
            }
        }

        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
    }
}
