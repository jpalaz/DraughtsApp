package by.draughts.model.game;

import java.util.List;

public class Position {
    private List<Draught> draughts;
    private boolean isWhiteMove;

    public boolean getIsWhiteMove() {
        return isWhiteMove;
    }

    public void setIsWhiteMove(boolean isWhiteMove) {
        this.isWhiteMove = isWhiteMove;
    }

    public Position() {
    }

    public List<Draught> getDraughts() {
        return draughts;
    }

    public void setDraughts(List<Draught> draughts) {
        this.draughts = draughts;
    }
}
