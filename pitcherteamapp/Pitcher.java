/*
    Created by:    Hilary Philistin,
                   Matthew Blake, 
                   Henry Sesay, 
                   Braden Henry, 
                   Zach Thompson, 
                   Wyatt Metcalf
    Created on:    05/08/2025
    Team’s name:   Pitcher
    Description:   Create a baseball statistics program that produce a report's 
                   list of the statistics for all pitchers in that game and 
                   calculates the earned run average for each pitcher.
                   Updated to include all pitcher statistics necessary for multi-game summaries.
*/

package csd2522.wrm.mavenproject1;

// The Pitcher class takes a pitcher's statistics and calculates ERA.
// App uses 10 fields. This only contained 3 previously, now has the 10 fields. - 05-09-25
public class Pitcher {
    private final String name;
    private final double inningsPitched;
    private final int hits;
    private final int runs;
    private final int earnedRuns;
    private final int baseOnBalls;
    private final int strikeouts;
    private final int atBats;
    private final int battersFaced;
    private final int numberOfPitches;

    public Pitcher(String name, double inningsPitched, int earnedRuns, int hits, int runs,
                   int baseOnBalls, int strikeouts, int atBats, int battersFaced, int numberOfPitches) {
        this.name = name;
        this.inningsPitched = inningsPitched;
        this.earnedRuns = earnedRuns;
        this.hits = hits;
        this.runs = runs;
        this.baseOnBalls = baseOnBalls;
        this.strikeouts = strikeouts;
        this.atBats = atBats;
        this.battersFaced = battersFaced;
        this.numberOfPitches = numberOfPitches;
    }

    public String getName() {
        return name;
    }

    public double getInningsPitched() {
        return inningsPitched;
    }

    public int getHits() {
        return hits;
    }

    public int getRuns() {
        return runs;
    }

    public int getEarnedRuns() {
        return earnedRuns;
    }

    public int getBaseOnBalls() {
        return baseOnBalls;
    }

    public int getStrikeouts() {
        return strikeouts;
    }

    public int getAtBats() {
        return atBats;
    }

    public int getBattersFaced() {
        return battersFaced;
    }

    public int getNumberOfPitches() {
        return numberOfPitches;
    }

    // Calculate ERA: (earnedRuns * 9) divided by innings pitched.
    public double calculateERA() {
        if (inningsPitched == 0) return 0;
        return (earnedRuns * 9) / inningsPitched;
    }

    @Override
    public String toString() {
        return String.format("%s: IP=%.2f, ER=%d, ERA=%.2f",
                name, inningsPitched, earnedRuns, calculateERA());
    }
}


