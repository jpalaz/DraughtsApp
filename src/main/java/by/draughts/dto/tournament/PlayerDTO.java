package by.draughts.dto.tournament;

import by.draughts.model.person.PersonName;
import by.draughts.model.person.Player;

public class PlayerDTO {
    private PersonName name;
    private short points;
    private byte gamesPlayed;

    public PlayerDTO(Player player) {
        name = player.getName();
        points = player.getPoints();
        gamesPlayed = player.getGamesPlayed();
    }

    public PlayerDTO(String name) {
        this.name = new PersonName(name);
        points = 0;
        gamesPlayed = 0;
    }

    public PlayerDTO() {
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
}
