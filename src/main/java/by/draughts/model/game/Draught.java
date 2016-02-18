package by.draughts.model.game;

public class Draught {
    private boolean isWhite;
    private boolean isKing;
    private boolean isBeaten;
    private int row;
    private int column;
    private int position;

    public Draught() {
    }

    public Draught(Draught obj) {
        isWhite = obj.isWhite();
        isKing = obj.isKing();
        isBeaten = obj.isBeaten();
        row = obj.getRow();
        column = obj.getColumn();
        position = obj.getPosition();
    }

    public void setDraught(Draught obj) {
        isWhite = obj.isWhite;
        isKing = obj.isKing;
        isBeaten = obj.isBeaten;
        row = obj.row;
        column = obj.column;
        position = obj.position;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setIsKing(boolean isKing) {
        this.isKing = isKing;
    }

    public boolean isBeaten() {
        return isBeaten;
    }

    public void setIsBeaten(boolean isBeaten) {
        this.isBeaten = isBeaten;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isKingField() {
        if (!isKing && ((isWhite && row == 0) || (!isWhite && row == 7))) {
            return true;
        } else {
            return false;
        }
    }

    public static int transformCoordinates(int row, int column) {
        if (row % 2 == 0) {
            return row * 4 + (column + 1) / 2;
        } else {
            return row * 4 + column / 2 + 1;
        }
    }

    public static int transformGetRow(int position) {
        return (position - 1) / 4;
    }

    public static int transformGetColumn(int position) {
        position--;
        if (position % 4 == 0) {
            return (position % 4) * 2 + 1;
        } else {
            return (position % 4) * 2;
        }
    }

    public static boolean isInBoard(int row, int column) {
        if (row >= 0 && row <= 7 && column >= 0 && column <= 7) {
            return true;
        }
        return false;
    }

    public void moveDraught(int position) {
        this.position = position;
        row = transformGetRow(position);
        column = transformGetColumn(position);
        if (isKingField()) {
            isKing = true;
        }
    }

    public void moveDraught(int row, int column) {
        this.row = row;
        this.column = column;
        position = transformCoordinates(row, column);
        if (isKingField()) {
            isKing = true;
        }
    }
}
