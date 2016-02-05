package by.draughts.dto.game;

import by.draughts.dto.tournament.PlayerDTO;
import by.draughts.model.game.GameResult;

public class GameTitleDTO {
    private PlayerDTO white;
    private PlayerDTO black;
    private GameResult result;

    public GameTitleDTO() {
    }

    public PlayerDTO getWhite() {
        return white;
    }

    public void setWhite(PlayerDTO white) {
        this.white = white;
    }

    public PlayerDTO getBlack() {
        return black;
    }

    public void setBlack(PlayerDTO black) {
        this.black = black;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }
}
