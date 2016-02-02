package by.draughts.service.impl;

import by.draughts.model.PlyMetadata;
import by.draughts.model.Position;

import java.util.ArrayList;
import java.util.List;

public class PositionService{
    private Field[][] board;
    private Position position;
    private List<PlyMetadata> possibleMoves;

    private enum Field{
        WHITE, BLACK, WHITE_KING, BLACK_KING, BEATEN, EMPTY
    }

    private void fillPositions(List<Byte> draught, Field fillField){
        if(draught != null){
            for(Byte obj : draught){
                if(obj >= 0 && obj <= 32){
                    if(((obj - 1) / 4) % 2 == 0){
                        board[(obj - 1) / 4][((obj - 1) % 4) * 2 + 1] = fillField;
                    } else{
                        board[(obj - 1) / 4][((obj - 1) % 4) * 2] = fillField;
                    }
                }
            }
        }
    }

    private byte transformCoordinates(int i, int j){
        if(i % 2 == 0){
            return (byte) (i * 4 + (j + 1) / 2);
        } else{
            return (byte) (i * 4 + j / 2 + 1);
        }
    }

    private void fillBoard(){
        board = new Field[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = Field.EMPTY;
            }
        }

        fillPositions( position.getBlackKings(), Field.BLACK_KING );
        fillPositions( position.getWhiteKings(), Field.WHITE_KING );
        fillPositions( position.getBlacks(), Field.BLACK );
        fillPositions( position.getWhites(), Field.WHITE );
    }

    private boolean isInBoard(int row, int column){
        if(row >= 0 && row <= 7 && column >= 0 && column <= 7){
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        Position temp = new Position();
        ArrayList<Byte> draughts = new ArrayList<>();


        draughts.add( (byte) 13 );
        temp.setBlacks( draughts );

        draughts = new ArrayList<>();
        draughts.add( (byte) 9 );
        draughts.add( (byte) 10 );
        draughts.add( (byte) 18 );
        draughts.add( (byte) 11 );
        draughts.add( (byte) 19 );
        draughts.add( (byte) 27 );
        draughts.add( (byte) 26 );
        draughts.add( (byte) 25 );
        temp.setWhites( draughts );

        PositionService o = new PositionService();
        List<PlyMetadata> obj = o.getPossibleMoves( temp );
    }

    private boolean possibleBeat(PlyMetadata move, int row, int column, int rowDirection, int columnDirection, Field beatenColour){
        if(isInBoard( row + 2 * rowDirection, column + 2 * columnDirection ) && board[row + rowDirection][column + columnDirection] == beatenColour &&
                board[row + 2 * rowDirection][column + 2 * columnDirection] == Field.EMPTY){
            board[row + rowDirection][column + columnDirection] = Field.BEATEN;
            if(move == null){
                move = new PlyMetadata();
                move.setFrom( transformCoordinates( row, column ) );
                move.setBeat( true );
            } else{
                if(move.getIntermediats() == null){
                    move.setIntermediats( new ArrayList<>() );
                }
                move.addIntermediat( move.getTo() );

            }
            move.setTo( transformCoordinates( row + 2 * rowDirection, column + 2 * columnDirection ) );

            boolean continueBeat1 = possibleBeat( move, row + 2 * rowDirection, column + 2 * columnDirection, -1, -1, beatenColour );
            boolean continueBeat2 = possibleBeat( move, row + 2 * rowDirection, column + 2 * columnDirection, -1, 1, beatenColour );
            boolean continueBeat3 = possibleBeat( move, row + 2 * rowDirection, column + 2 * columnDirection, 1, -1, beatenColour );
            boolean continueBeat4 = possibleBeat( move, row + 2 * rowDirection, column + 2 * columnDirection, 1, 1, beatenColour );
            if(!continueBeat1 && !continueBeat2 && !continueBeat3 && !continueBeat4){
                possibleMoves.add( new PlyMetadata( move ) );
            }
            if(move.getIntermediats() != null && !move.getIntermediats().isEmpty()){
                move.setTo( move.getIntermediats().remove( move.getIntermediats().size() - 1 ) );
            }
            board[row + rowDirection][column + columnDirection] = beatenColour;
            return true;
        }
        return false;
    }

    private void possibleMove(int row, int column, int rowDirection, int columnDirection){
        if(isInBoard( row + rowDirection, column + columnDirection ) && board[row + rowDirection][column + columnDirection] == Field.EMPTY){
            PlyMetadata move = new PlyMetadata();
            move.setFrom( transformCoordinates( row, column ) );
            move.setTo( transformCoordinates( row + rowDirection, column + columnDirection ) );
            possibleMoves.add( move );
        }
    }

    private void movesForDraught(Field colour){
        boolean allowSilentMoves = true;
        int step;
        Field oppositeColour;
        if(colour == Field.WHITE){
            step = -1;
            oppositeColour = Field.BLACK;
        } else{
            step = 1;
            oppositeColour = Field.WHITE;
        }
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] == colour){
                    board[i][j] = Field.EMPTY;
                    boolean possibleBeat1 = possibleBeat( null, i, j, -1, -1, oppositeColour );
                    boolean possibleBeat2 = possibleBeat( null, i, j, -1, 1, oppositeColour );
                    boolean possibleBeat3 = possibleBeat( null, i, j, 1, -1, oppositeColour );
                    boolean possibleBeat4 = possibleBeat( null, i, j, 1, 1, oppositeColour );
                    if(possibleBeat1 || possibleBeat2 || possibleBeat3 || possibleBeat4){
                        allowSilentMoves = false;
                    }
                    board[i][j] = colour;
                }
            }
        }
        if(allowSilentMoves){
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(board[i][j] == colour){
                        possibleMove( i, j, step, -1 );
                        possibleMove( i, j, step, 1 );
                    }
                }
            }
        }
    }

    public List<PlyMetadata> getPossibleMoves(Position obj){
        position = obj;
        fillBoard();
        possibleMoves = new ArrayList<>();

        if(obj.isWhiteMove()){
            movesForDraught( Field.WHITE );
        } else{
            movesForDraught( Field.BLACK );
        }
        return possibleMoves;
    }
}
