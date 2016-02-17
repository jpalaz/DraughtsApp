package by.draughts.service.impl;

import by.draughts.model.ply.PlyPosition;
import by.draughts.model.game.Position;
import by.draughts.service.PositionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    private Field[][] board;
    private Position position;
    private List<PlyPosition> possibleMoves;

    private enum Field {
        WHITE, BLACK, WHITE_KING, BLACK_KING, BEATEN, EMPTY
    }

    private int getRow(byte draught) {
        return (draught - 1) / 4;
    }

    private int getColumn(byte draught) {
        if (((draught - 1) / 4) % 2 == 0) {
            return ((draught - 1) % 4) * 2 + 1;
        } else {
            return ((draught - 1) % 4) * 2;
        }
    }

    private void fillPositions(List<Byte> draught, Field fillField) {
        if (draught != null) {
            for (Byte obj : draught) {
                if (obj >= 0 && obj <= 32) {
                    if (getRow(obj) == 0) {
                        board[getRow(obj)][getColumn(obj)] = fillField;
                    } else {
                        board[getRow(obj)][getColumn(obj)] = fillField;
                    }
                }
            }
        }
    }

    private byte transformCoordinates(int i, int j) {
        if (i % 2 == 0) {
            return (byte) (i * 4 + (j + 1) / 2);
        } else {
            return (byte) (i * 4 + j / 2 + 1);
        }
    }

    private void fillBoard() {
        board = new Field[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = Field.EMPTY;
            }
        }

        fillPositions(position.getBlackKings(), Field.BLACK_KING);
        fillPositions(position.getWhiteKings(), Field.WHITE_KING);
        fillPositions(position.getBlacks(), Field.BLACK);
        fillPositions(position.getWhites(), Field.WHITE);
    }

    private boolean isInBoard(int row, int column) {
        if (row >= 0 && row <= 7 && column >= 0 && column <= 7) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Position temp = new Position();
        ArrayList<Byte> draughts = new ArrayList<>();


        draughts.add((byte) 1);
        temp.setBlacks(draughts);

        draughts = new ArrayList<>();
        draughts.add((byte) 17);
        draughts.add((byte) 26);
        draughts.add((byte) 9);
        temp.setWhites(draughts);

        PositionServiceImpl o = new PositionServiceImpl();
        List<PlyPosition> obj = o.getPossibleMoves(temp);
    }

    private void setTo(int draught) {
        if(position.getIsWhiteMove()){
            board[getRow((byte)draught)][getColumn((byte)draught)] =Field.WHITE;
        }else{
            board[getRow((byte)draught)][getColumn((byte)draught)] =Field.BLACK;
        }
    }

    private void unsetTo(int draught) {
        board[getRow((byte)draught)][getColumn((byte)draught)] =Field.EMPTY;
    }

    private List<Byte> setListOfDraughts(List<Byte> obj) {
        if (!obj.isEmpty()) {
            return obj;
        } else {
            return null;
        }
    }

    private Position getCurrentPosition() {
        Position result = new Position();
        result.setIsWhiteMove(!position.getIsWhiteMove());
        List<Byte> whites = new ArrayList<>();
        List<Byte> whiteKings = new ArrayList<>();
        List<Byte> blacks = new ArrayList<>();
        List<Byte> blackKings = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (board[i][j]) {
                    case WHITE:
                        whites.add(transformCoordinates(i, j));
                        break;
                    case WHITE_KING:
                        whiteKings.add(transformCoordinates(i, j));
                        break;
                    case BLACK:
                        blacks.add(transformCoordinates(i, j));
                        break;
                    case BLACK_KING:
                        blackKings.add(transformCoordinates(i, j));
                        break;
                }
            }
        }
        result.setWhites(setListOfDraughts(whites));
        result.setWhiteKings(setListOfDraughts(whiteKings));
        result.setBlacks(setListOfDraughts(blacks));
        result.setBlackKings(setListOfDraughts(blackKings));
        return result;
    }

    private boolean possibleBeat(PlyPosition move, int row, int column, int rowDirection, int columnDirection, Field beatenColour) {
        if (isInBoard(row + 2 * rowDirection, column + 2 * columnDirection) && board[row + rowDirection][column + columnDirection] == beatenColour &&
                board[row + 2 * rowDirection][column + 2 * columnDirection] == Field.EMPTY) {
            board[row + rowDirection][column + columnDirection] = Field.BEATEN;
            if (move == null) {
                move = new PlyPosition();
                move.setFrom(transformCoordinates(row, column));
                move.setBeat(true);
            } else {
                if (move.getIntermediats() == null) {
                    move.setIntermediats(new ArrayList<>());
                }
                move.addIntermediat(move.getTo());

            }
            move.setTo(transformCoordinates(row + 2 * rowDirection, column + 2 * columnDirection));

            boolean continueBeat1 = possibleBeat(move, row + 2 * rowDirection, column + 2 * columnDirection, -1, -1, beatenColour);
            boolean continueBeat2 = possibleBeat(move, row + 2 * rowDirection, column + 2 * columnDirection, -1, 1, beatenColour);
            boolean continueBeat3 = possibleBeat(move, row + 2 * rowDirection, column + 2 * columnDirection, 1, -1, beatenColour);
            boolean continueBeat4 = possibleBeat(move, row + 2 * rowDirection, column + 2 * columnDirection, 1, 1, beatenColour);
            if (!continueBeat1 && !continueBeat2 && !continueBeat3 && !continueBeat4) {
                setTo(move.getTo());
                PlyPosition temp = new PlyPosition(move);
                temp.setPosition(getCurrentPosition());
                possibleMoves.add(temp);
                unsetTo(move.getTo());
            }
            if (move.getIntermediats() != null && !move.getIntermediats().isEmpty()) {
                move.setTo(move.getIntermediats().remove(move.getIntermediats().size() - 1));
            }
            board[row + rowDirection][column + columnDirection] = beatenColour;
            return true;
        }
        return false;
    }

    private void possibleMove(int row, int column, int rowDirection, int columnDirection) {
        if (isInBoard(row + rowDirection, column + columnDirection) && board[row + rowDirection][column + columnDirection] == Field.EMPTY) {
            PlyPosition move = new PlyPosition();
            move.setFrom(transformCoordinates(row, column));
            move.setTo(transformCoordinates(row + rowDirection, column + columnDirection));
            setTo(move.getTo());
            move.setPosition(getCurrentPosition());
            possibleMoves.add(move);
            unsetTo(move.getTo());
        }
    }

    private void movesForDraught(Field colour) {
        boolean allowSilentMoves = true;
        int step;
        Field oppositeColour;
        if (colour == Field.WHITE) {
            step = -1;
            oppositeColour = Field.BLACK;
        } else {
            step = 1;
            oppositeColour = Field.WHITE;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == colour) {
                    board[i][j] = Field.EMPTY;
                    boolean possibleBeat1 = possibleBeat(null, i, j, -1, -1, oppositeColour);
                    boolean possibleBeat2 = possibleBeat(null, i, j, -1, 1, oppositeColour);
                    boolean possibleBeat3 = possibleBeat(null, i, j, 1, -1, oppositeColour);
                    boolean possibleBeat4 = possibleBeat(null, i, j, 1, 1, oppositeColour);
                    if (possibleBeat1 || possibleBeat2 || possibleBeat3 || possibleBeat4) {
                        allowSilentMoves = false;
                    }
                    board[i][j] = colour;
                }
            }
        }
        if (allowSilentMoves) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] == colour) {
                        board[i][j] = Field.EMPTY;
                        possibleMove(i, j, step, -1);
                        possibleMove(i, j, step, 1);
                        board[i][j] = colour;
                    }
                }
            }
        }
    }

    @Override
    public List<PlyPosition> getPossibleMoves(Position obj) {
        position = obj;
        fillBoard();
        possibleMoves = new ArrayList<>();

        if (obj.getIsWhiteMove()) {
            movesForDraught(Field.WHITE);
        } else {
            movesForDraught(Field.BLACK);
        }
        return possibleMoves;
    }
}
