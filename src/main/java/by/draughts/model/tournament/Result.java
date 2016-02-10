package by.draughts.model.tournament;

import by.draughts.dto.tournament.PlayerDTO;
import by.draughts.model.person.Player;

public class Result implements Comparable<Result> {
    private int points;
    private boolean isWhite;
    private int place;
    private int opponentSorting;
    private Coefficient coefficient;

    public Result() {
    }

    public Result(int opponentSorting, boolean isWhite, int points) {
        this.opponentSorting = opponentSorting;
        this.isWhite = isWhite;
        this.points = points;
    }

    @Override
    public int compareTo(Result o) {
        return place - o.place;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getOpponentSorting() {
        return opponentSorting;
    }

    public void setOpponentSorting(int opponentSorting) {
        this.opponentSorting = opponentSorting;
    }

    public Coefficient getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Coefficient coefficient) {
        this.coefficient = coefficient;
    }
}
