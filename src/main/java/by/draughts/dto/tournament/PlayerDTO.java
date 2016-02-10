package by.draughts.dto.tournament;

import by.draughts.model.person.Person;
import by.draughts.model.person.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerDTO {
    private Person person;
    private byte gamesPlayed;

    public PlayerDTO(Player player) {
        gamesPlayed = player.getGamesPlayed();
    }

    public PlayerDTO(String name) {
        this.person = new Person(name);
    }

    public PlayerDTO() {
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public byte getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(byte gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
}
