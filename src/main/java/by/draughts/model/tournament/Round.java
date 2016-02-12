package by.draughts.model.tournament;

import by.draughts.dto.tournament.GameTitleDTO;
import by.draughts.model.game.GameResult;
import by.draughts.model.game.GameTitle;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Round {
    private Date date;
    private List<GameTitle> games;
    private int roundNumber;

    public Round() {
    }

    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Round(Date date, List<GameTitle> games, int roundNumber) {
        this.date = date;
        this.games = games;
        this.roundNumber = roundNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<GameTitle> getGames() {
        return games;
    }

    public void setGames(List<GameTitle> games) {
        this.games = games;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void setCurrentGamesResults(List<GameTitleDTO> resultedGames) {
        Collections.sort(resultedGames);
        for (int i = 0, white, black; i < games.size(); i++) {
            GameTitle game = games.get(i);
            GameResult gameResult = resultedGames.get(i).getResult();
            if (game.getResult() == GameResult.UNDEFINED && gameResult != GameResult.UNDEFINED) {
                game.getWhite().increaseGamesPlayed();
                game.getBlack().increaseGamesPlayed();
            }
            game.setResult(gameResult);
            switch (game.getResult()) {
                case WHITE_WON:
                    white = 2;
                    black = 0;
                    break;
                case DRAW:
                    white = black = 1;
                    break;
                case BLACK_WON:
                    white = 0;
                    black = 2;
                    break;
                default:
                    white = 0;
                    black = 0;
                    break;
            }
            Result result = game.getWhite().getResult(roundNumber - 1);
            result.setOpponentSorting(game.getBlack().getSortingNumber());
            result.setWhite(true);
            result.setPoints(white);

            result = game.getBlack().getResult(roundNumber - 1);
            result.setOpponentSorting(game.getWhite().getSortingNumber());
            result.setWhite(false);
            result.setPoints(black);
        }
    }
}
