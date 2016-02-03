package by.draughts.model.tournament;

import by.draughts.dto.tournament.PlayerDTO;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private PersonName name;
    private short points;
    private byte gamesPlayed;
    private List<Player> rivals;

    public Player() {
    }

    public Player(String surname) {
        name = new PersonName(surname);
    }

    public Player(PlayerDTO playerDTO) {
        name = playerDTO.getName();
        points = playerDTO.getPoints();
        gamesPlayed = playerDTO.getGamesPlayed();
        rivals = new ArrayList<>();
    }

    public PersonName getName() {
        return name;
    }

    public void setName(PersonName name) {
        this.name = name;
    }

    public short getPoints() {
        return points;
    }

    public void setPoints(short points) {
        this.points = points;
    }

    public byte getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(byte gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public List<Player> getRivals() {
        return rivals;
    }

    public void setRivals(List<Player> rivals) {
        this.rivals = rivals;
    }
}
