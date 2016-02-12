package by.draughts.model.person;

import by.draughts.dto.tournament.PlayerDTO;
import by.draughts.model.tournament.Coefficient;
import by.draughts.model.tournament.Result;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Player {
    private Person person;
    private byte gamesPlayed;
    private int sortingNumber;
    private List<Result> results;

    private final static SortingComparator sortingComparator = new SortingComparator();

    public Player() {
        System.out.println("Empty Player");
        results = new ArrayList<>();
    }

    public Player(String surname) {
        System.out.println("String Player");
        person = new Person(surname);
        gamesPlayed = 0;
        results = new ArrayList<>();
    }

    public Player(PlayerDTO playerDTO) {
        System.out.println("DTO Player");
        person = playerDTO.getPerson();
        gamesPlayed = playerDTO.getGamesPlayed();
        results = new ArrayList<>();
    }

    public void increaseGamesPlayed() {
        gamesPlayed++;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person name) {
        this.person = name;
    }

    public byte getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(byte gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public List<Result> getResults() {
        return results;
    }

    public Result getResult(int index) {
        return results.get(index);
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public void setResults(int size) {
        results = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            results.add(new Result());
        }
    }
}