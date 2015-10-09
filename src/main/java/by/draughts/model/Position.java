package by.draughts.model;

import java.util.List;

public class Position {
    private List<Integer> whites;
    private List<Integer> whiteKings;
    private List<Integer> blacks;
    private List<Integer> blackKings;

    public Position() {
    }

    public List<Integer> getWhites() {
        return whites;
    }

    public void setWhites(List<Integer> whites) {
        this.whites = whites;
    }

    public List<Integer> getWhiteKings() {
        return whiteKings;
    }

    public void setWhiteKings(List<Integer> whiteKings) {
        this.whiteKings = whiteKings;
    }

    public List<Integer> getBlacks() {
        return blacks;
    }

    public void setBlacks(List<Integer> blacks) {
        this.blacks = blacks;
    }

    public List<Integer> getBlackKings() {
        return blackKings;
    }

    public void setBlackKings(List<Integer> blackKings) {
        this.blackKings = blackKings;
    }
}
