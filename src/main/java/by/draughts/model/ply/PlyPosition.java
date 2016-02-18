package by.draughts.model.ply;

import by.draughts.model.game.Position;

import java.util.ArrayList;
import java.util.List;

public class PlyPosition {
    private byte from;
    private byte to;
    private List<Integer> intermediates;
    private boolean isBeat;
    private Position position;

    public PlyPosition() {
        isBeat = false;
    }

    public PlyPosition(PlyPosition obj) {
        from = obj.from;
        to = obj.to;
        isBeat = obj.isBeat;
        if (obj.intermediates != null) {
            intermediates = new ArrayList<>(obj.intermediates);
        }
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Integer> getIntermediates() {
        return intermediates;
    }

    public void setIntermediates(List<Integer> intermediates) {
        this.intermediates = intermediates;
    }

    public void addIntermediat(int intermediat) {
        intermediates.add(intermediat);
    }

    public void setIsBeat(boolean isBeat) {
        this.isBeat = isBeat;
    }

    public byte getFrom() {
        return from;
    }

    public void setFrom(byte from) {
        this.from = from;
    }

    public byte getTo() {
        return to;
    }

    public void setTo(byte to) {
        this.to = to;
    }

    public boolean isBeat() {
        return isBeat;
    }

    public void setBeat(boolean beat) {
        this.isBeat = beat;
    }
}
