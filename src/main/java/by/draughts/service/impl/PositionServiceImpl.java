package by.draughts.service.impl;

import by.draughts.model.game.Draught;
import by.draughts.model.ply.PlyPosition;
import by.draughts.model.game.Position;
import by.draughts.service.PositionService;

import java.util.ArrayList;
import java.util.List;

public class PositionServiceImpl implements PositionService {
    private Draught[][] board;
    private Position position;
    private List<PlyPosition> possibleMoves;
    private Draught currantDraught;
    private int startPosition;

    private void fillBoard() {
        board = new Draught[8][8];
        for (Draught obj : position.getDraughts()) {
            board[obj.getRow()][obj.getColumn()] = obj;
        }
    }

    private void savePly(int to, List<Integer> intermediates) {
        PlyPosition move = new PlyPosition();
        move.setFrom((byte) startPosition);
        move.setTo((byte) to);
        if (intermediates != null && !intermediates.isEmpty()) {
            move.setIntermediates(new ArrayList<>(intermediates));
        }
        move.setPosition(getCurrentPosition());
        possibleMoves.add(move);
    }

    private Position getCurrentPosition() {
        Position result = new Position();
        result.setIsWhiteMove(!position.getIsWhiteMove());
        List<Draught> draughts = new ArrayList<>();
        for (Draught obj : position.getDraughts()) {
            if (!obj.isBeaten()) {
                draughts.add(new Draught(obj));
            }
        }
        result.setDraughts(draughts);
        return result;
    }

    private void movesForDraught() {
        boolean allowSilentMoves = true;
        for (Draught obj : position.getDraughts()) {
            if (obj.isWhite() == position.getIsWhiteMove()) {
                currantDraught = obj;
                if (possibleBeat()) {
                    allowSilentMoves = false;
                }
            }
        }
        if (allowSilentMoves) {
            for (Draught obj : position.getDraughts()) {
                if (obj.isWhite() == position.getIsWhiteMove()) {
                    currantDraught = obj;
                    possibleMoves();
                }
            }
        }
    }

    private void possibleMoves() {
        startPosition = currantDraught.getPosition();
        if (currantDraught.isKing()) {
            moveDraught(1, -1);
            moveDraught(1, 1);
            moveDraught(-1, -1);
            moveDraught(-1, 1);
        } else {
            if (currantDraught.isWhite()) {
                moveDraught(-1, -1);
                moveDraught(-1, 1);
            } else {
                moveDraught(1, -1);
                moveDraught(1, 1);
            }
        }
    }

    private boolean possibleBeat() {
        startPosition = currantDraught.getPosition();
        return beatDraught(null, -1, -1) | beatDraught(null, -1, 1) | beatDraught(null, 1, -1) | beatDraught(null, 1, 1);
    }

    private void moveDraught(int rowDirect, int columnDirect) {
        int row = currantDraught.getRow();
        int column = currantDraught.getColumn();
        int mul = 1;
        do {
            if (Draught.isInBoard(row + mul * rowDirect, column + mul * columnDirect) && board[row + mul * rowDirect][column + mul * columnDirect] == null) {
                Draught temp = new Draught(currantDraught);
                currantDraught.moveDraught(row + mul * rowDirect, column + mul * columnDirect);
                savePly(currantDraught.getPosition(), null);
                currantDraught.setDraught(temp);
                mul++;
            } else {
                break;
            }
        } while (currantDraught.isKing());
    }

    private boolean beatDraught(List<Integer> intermediates, int rowDirect, int columnDirect) {
        int row = currantDraught.getRow();
        int column = currantDraught.getColumn();
        int mul = 1;
        int mulAfter = 0;
        boolean canSave = true;
        List<Integer> to = new ArrayList<>();
        while (Draught.isInBoard(row + mul * rowDirect, column + mul * columnDirect) && currantDraught.isKing()
                && board[row + mul * rowDirect][column + mul * columnDirect] == null) {
            mul++;
        }
        if (Draught.isInBoard(row + (mul + 1) * rowDirect, column + (mul + 1) * columnDirect)
                && board[row + (mul + 1) * rowDirect][column + (mul + 1) * columnDirect] == null
                && board[row + mul * rowDirect][column + mul * columnDirect] != null
                && board[row + mul * rowDirect][column + mul * columnDirect].isWhite() != currantDraught.isWhite()
                && board[row + mul * rowDirect][column + mul * columnDirect].isBeaten() == false) {
            Draught temp = new Draught(currantDraught);
            if (intermediates == null) {
                intermediates = new ArrayList<>();
            } else {
                intermediates.add(currantDraught.getPosition());
            }
            board[row + mul * rowDirect][column + mul * columnDirect].setIsBeaten(true);
            do {
                currantDraught.moveDraught(row + (mul + mulAfter + 1) * rowDirect, column + (mul + mulAfter + 1) * columnDirect);
                to.add(currantDraught.getPosition());
                board[row + (mul + mulAfter + 1) * rowDirect][column + (mul + mulAfter + 1) * columnDirect] = board[row][column];
                board[row][column] = null;
                if(mulAfter == 0 && beatDraught(intermediates, rowDirect, columnDirect)){
                    canSave = false;
                }
                if (beatDraught(intermediates, -rowDirect, columnDirect)
                        | beatDraught(intermediates, rowDirect, -columnDirect)) {
                    canSave = false;
                }
                board[row][column] = board[row + (mul + mulAfter + 1) * rowDirect][column + (mul + mulAfter + 1) * columnDirect];
                board[row + (mul + mulAfter + 1) * rowDirect][column + (mul + mulAfter + 1) * columnDirect] = null;
                mulAfter++;
            }
            while (Draught.isInBoard(row + (mul + mulAfter + 1) * rowDirect, column + (mul + mulAfter + 1) * columnDirect) && temp.isKing()
                    && board[row + (mul + mulAfter + 1) * rowDirect][column + (mul + mulAfter + 1) * columnDirect] == null);
            if(canSave){
                for(Integer obj:to) {
                    currantDraught.moveDraught(obj);
                    savePly(obj, intermediates);
                    currantDraught.setDraught(temp);
                }
            }
            board[row + mul * rowDirect][column + mul * columnDirect].setIsBeaten(false);
            currantDraught.setDraught(temp);
            if(!intermediates.isEmpty()){
                intermediates.remove(intermediates.size()-1);
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Position temp = new Position();
        ArrayList<Draught> draughts = new ArrayList<>();

        Draught dr = new Draught();
        dr.setRow(1);
        dr.setColumn(0);
        dr.setIsKing(true);
        dr.setPosition(Draught.transformCoordinates(dr.getRow(), dr.getColumn()));
        draughts.add(dr);

        dr = new Draught();
        dr.setRow(2);
        dr.setColumn(1);
        dr.setIsWhite(true);
        dr.setPosition(Draught.transformCoordinates(dr.getRow(), dr.getColumn()));
        draughts.add(dr);

        dr = new Draught();
        dr.setRow(5);
        dr.setColumn(4);
        dr.setIsWhite(true);
        dr.setPosition(Draught.transformCoordinates(dr.getRow(), dr.getColumn()));
        draughts.add(dr);

        dr = new Draught();
        dr.setRow(5);
        dr.setColumn(6);
        dr.setIsWhite(true);
        dr.setPosition(Draught.transformCoordinates(dr.getRow(), dr.getColumn()));
        draughts.add(dr);

        dr = new Draught();
        dr.setRow(3);
        dr.setColumn(6);
        dr.setIsWhite(true);
        dr.setPosition(Draught.transformCoordinates(dr.getRow(), dr.getColumn()));
        draughts.add(dr);

        temp.setDraughts(draughts);
        PositionServiceImpl o = new PositionServiceImpl();
        List<PlyPosition> obj = o.getPossibleMoves(temp);
    }

    public List<PlyPosition> getPossibleMoves(Position obj) {
        position = obj;
        fillBoard();
        possibleMoves = new ArrayList<>();
        movesForDraught();
        return possibleMoves;
    }
}
