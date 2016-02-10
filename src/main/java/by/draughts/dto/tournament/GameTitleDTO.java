package by.draughts.dto.tournament;

import by.draughts.model.game.GameResult;

public class GameTitleDTO implements Comparable<GameTitleDTO> {
    private int id;
    private GameResult result;

    public GameTitleDTO() {
    }

    @Override
    public int compareTo(GameTitleDTO game) {
        return this.getId() - game.getId();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }
}
