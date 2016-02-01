package by.draughts.model;

import java.util.ArrayList;
import java.util.List;

public class Sequence {
    private List<Ply> moves;

    public Sequence() {
        moves = new ArrayList<>();
    }

    public List<Ply> getMoves() {
        return moves;
    }

    public void setMoves(List<Ply> moves) {
        this.moves = moves;
    }

    public void add(Ply ply) {
        moves.add(ply);
    }
}
