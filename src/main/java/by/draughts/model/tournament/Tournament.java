package by.draughts.model.tournament;

import by.draughts.model.exception.NoSuchRoundException;
import by.draughts.model.game.GameTitle;

import java.util.Date;
import java.util.List;

public class Tournament {
    private String name;
    private String place;
    private String arbiter;
    private int roundAmount;
    private int currentRound;
    private Date begin;
    private Date end;

    private TournamentSystem system;
    private List<Round> rounds;
    private List<Player> players;

    public Tournament() {
    }

    public List<GameTitle> getRoundGames(int round) throws NoSuchRoundException {
        round--;
        if (rounds != null && round >= 0 && round < rounds.size())
            return rounds.get(round).getGames();
        throw new NoSuchRoundException();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getArbiter() {
        return arbiter;
    }

    public void setArbiter(String arbiter) {
        this.arbiter = arbiter;
    }

    public int getRoundAmount() {
        return roundAmount;
    }

    public void setRoundAmount(int roundAmount) {
        this.roundAmount = roundAmount;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public TournamentSystem getSystem() {
        return system;
    }

    public void setSystem(TournamentSystem system) {
        this.system = system;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
