package by.draughts.model.tournament;

public class Coefficient {
    private int solkoff;
    private int solkoffMedian;
    private int solkoffTrunkated;
    private int sonnenbornBerger;
    private int averageOpponentRating;
    private int rating;
    private int drawingOfLots;

    public Coefficient() {
    }

    public int getSolkoff() {
        return solkoff;
    }

    public void setSolkoff(int solkoff) {
        this.solkoff = solkoff;
    }

    public int getSolkoffMedian() {
        return solkoffMedian;
    }

    public void setSolkoffMedian(int solkoffMedian) {
        this.solkoffMedian = solkoffMedian;
    }

    public int getSolkoffTrunkated() {
        return solkoffTrunkated;
    }

    public void setSolkoffTrunkated(int solkoffTrunkated) {
        this.solkoffTrunkated = solkoffTrunkated;
    }

    public int getSonnenbornBerger() {
        return sonnenbornBerger;
    }

    public void setSonnenbornBerger(int sonnenbornBerger) {
        this.sonnenbornBerger = sonnenbornBerger;
    }

    public int getAverageOpponentRating() {
        return averageOpponentRating;
    }

    public void setAverageOpponentRating(int averageOpponentRating) {
        this.averageOpponentRating = averageOpponentRating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getDrawingOfLots() {
        return drawingOfLots;
    }

    public void setDrawingOfLots(int drawingOfLots) {
        this.drawingOfLots = drawingOfLots;
    }
}
