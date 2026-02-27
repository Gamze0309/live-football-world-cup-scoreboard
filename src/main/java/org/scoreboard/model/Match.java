package org.scoreboard.model;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;

    public Match(String homeTeam, String awayTeam) {
        String normalizedHomeTeam = validateAndNormalizeTeamName(homeTeam);
        String normalizedAwayTeam = validateAndNormalizeTeamName(awayTeam);

        validateTeamsDifferent(normalizedHomeTeam, normalizedAwayTeam);

        this.homeTeam = normalizedHomeTeam;
        this.awayTeam = normalizedAwayTeam;
    }

    public Match(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public static String validateAndNormalizeTeamName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }

        String normalized = name.trim();

        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }

        return normalized;
    }
    
    public static void validateTeamsDifferent(String homeTeam, String awayTeam) {
        if (homeTeam.equalsIgnoreCase(awayTeam)) {
            throw new IllegalArgumentException("Team names cannot be same");
        }
    }
}
