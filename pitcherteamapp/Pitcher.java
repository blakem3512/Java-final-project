/*
    Created by:  Hilary Philistin,
                 Matthew Blake, 
                 Henry Sesay, 
                 Braden Henry, 
                 Zach Thompson, 
                 Wyatt Metcalf
    Created on:  05/08/2025
    Team’s name: Pitcher
    Description: Create a baseball statistics program that produce a report's 
                 list of the statistics for all pitchers in that game and 
                 calculates the earned run average for each pitcher.
*/

package csd2522.wrm.mavenproject1;

// The Pitcher class takes a pitcher's statistics and calculates ERA.  
    
public class Pitcher {
    private final String name;
    private final double inningsPitched;
    private final int earnedRuns;

    public Pitcher(String name, double inningsPitched, int earnedRuns) {
        this.name = name;
        this.inningsPitched = inningsPitched;
        this.earnedRuns = earnedRuns;
    }

    public String getName() {
        return name;
    }

    public double getInningsPitched() {
        return inningsPitched;
    }

    public int getEarnedRuns() {
        return earnedRuns;
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

