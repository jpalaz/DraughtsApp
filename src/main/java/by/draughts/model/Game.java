package by.draughts.model;

import java.util.Date;

/*@Entity
@Table(name = "games")*/
public class Game {
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)*/
    private String id;

    private Metadata metadata;

    private String white;
    private String black;
    private GameResult result;
    private int gameType;

    private Position begin;
    private Sequence mainMoves;

    public Game() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Sequence getMainMoves() {
        return mainMoves;
    }

    public void setMainMoves(Sequence mainMoves) {
        this.mainMoves = mainMoves;
    }

    public String getWhite() {
        return white;
    }

    public void setWhite(String white) {
        this.white = white;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public Position getBegin() {
        return begin;
    }

    public void setBegin(Position begin) {
        this.begin = begin;
    }
}
