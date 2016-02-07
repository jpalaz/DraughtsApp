package by.draughts.model.person;

import by.draughts.dto.tournament.PlayerDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Player {
    private PersonName name;
    private short points;
    private byte gamesPlayed;
    private int sortingNumber;
    private int place;
    private List<Player> rivals;

    private final static SortingComparator sortingComparator = new SortingComparator();

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

    private static class SortingComparator implements Comparator<Player> {
        @Override
        public int compare(Player o1, Player o2) {
            return o1.getSortingNumber() - o2.getSortingNumber();
        }
    }

    public static SortingComparator getSortingComparator() {
        return sortingComparator;
    }

    public int getSortingNumber() {
        return sortingNumber;
    }

    public void setSortingNumber(int sortingNumber) {
        this.sortingNumber = sortingNumber;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
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
