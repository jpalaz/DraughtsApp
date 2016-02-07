package by.draughts.model.game;

import java.util.List;

public class Position {
    private List<Byte> whites;
    private List<Byte> whiteKings;
    private List<Byte> blacks;
    private List<Byte> blackKings;
    private boolean isWhiteMove;

    public boolean isWhiteMove() {
        return isWhiteMove;
    }

    public void setIsWhiteMove(boolean isWhiteMove) {
        this.isWhiteMove = isWhiteMove;
    }

    public Position() {
    }

    public List<Byte> getWhites() {
        return whites;
    }

    public void setWhites(List<Byte> whites) {
        this.whites = whites;
    }

    public List<Byte> getWhiteKings() {
        return whiteKings;
    }

    public void setWhiteKings(List<Byte> whiteKings) {
        this.whiteKings = whiteKings;
    }

    public List<Byte> getBlacks() {
        return blacks;
    }

    public void setBlacks(List<Byte> blacks) {
        this.blacks = blacks;
    }

    public List<Byte> getBlackKings() {
        return blackKings;
    }

    public void setBlackKings(List<Byte> blackKings) {
        this.blackKings = blackKings;
    }
}
