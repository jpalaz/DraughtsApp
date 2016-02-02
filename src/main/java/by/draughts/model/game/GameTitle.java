package by.draughts.model.game;

import by.draughts.model.tournament.Player;

public class GameTitle {
    private Player white;
    private Player black;
    private GameResult result;

    public GameTitle() {
    }

    public GameTitle(Player white, Player black) {
        this.white = white;
        this.black = black;
        this.result = GameResult.UNDEFINED;
    }

    public Player getWhite() {
        return white;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public Player getBlack() {
        return black;
    }

    public void setBlack(Player black) {
        this.black = black;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }
}
