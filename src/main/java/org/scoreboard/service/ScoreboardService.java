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
        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
    }
}
