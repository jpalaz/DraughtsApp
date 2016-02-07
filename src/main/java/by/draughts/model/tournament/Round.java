package by.draughts.model.tournament;

import by.draughts.model.game.GameTitle;
import by.draughts.model.person.Player;

import java.util.Date;
import java.util.List;

public class Round {
    private Date date;
    private List<GameTitle> games;
    private List<Player> players;

    public Round() {
    }

    public Round(Date date, List<GameTitle> games) {
        this.date = date;
        this.games = games;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
