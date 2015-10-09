package by.draughts.model;

import org.json.JSONArray;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/*@Entity
@Table(name = "games")*/
public class Game {
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)*/
    private String id;

    private String event;
    private String site;
    private Date date;
    private int round;
    private String white;
    private String black;
    private GameResult result;
    private int gameType;

    private Position beginPosition;
    private Ply firstMove;
    private List<Ply> alternatives;

    public Game() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
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

    public Position getBeginPosition() {
        return beginPosition;
    }

    public void setBeginPosition(Position beginPosition) {
        this.beginPosition = beginPosition;
    }

    public Ply getFirstMove() {
        return firstMove;
    }

    public void setFirstMove(Ply firstMove) {
        this.firstMove = firstMove;
    }

    public JSONArray generateMoves() {
        JSONArray movesLine = new JSONArray();
        firstMove.addToMovesJson(movesLine);
        /*if (alternatives != null) {
            for (Ply ply : alternatives)
                moves += ply.toStringPly();
        }*/
        return movesLine;//.toString();
    }
}
