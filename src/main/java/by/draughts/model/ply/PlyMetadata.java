package by.draughts.model.ply;

public class PlyMetadata {
    private short number;
    private byte from;
    private byte to;
    private boolean isWhiteSide;
    private boolean isBeat;

    public PlyMetadata() {
    }

    public short getNumber() {
        return number;
    }

    public void setNumber(short number) {
        this.number = number;
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

    public boolean isWhiteSide() {
        return isWhiteSide;
    }

    public void setWhiteSide(boolean whiteSide) {
        this.isWhiteSide = whiteSide;
    }

    public boolean isBeat() {
        return isBeat;
    }

    public void setBeat(boolean beat) {
        this.isBeat = beat;
    }
}
