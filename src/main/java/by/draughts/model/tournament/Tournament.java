package by.draughts.model.tournament;

import by.draughts.dto.tournament.PlayerDTO;
import by.draughts.dto.tournament.TournamentDTO;
import by.draughts.model.exception.NoSuchRoundException;
import by.draughts.model.game.GameTitle;
import by.draughts.model.person.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Tournament {
    private String name;
    private String place;
    private String arbiter;
    private int roundAmount;
    private int playedRounds;
    private Date begin;
    private Date end;
    private TournamentSystem system;
    private List<Round> rounds;
    private List<Player> players;

    public Tournament() {
        rounds = new ArrayList<>();
    }

    public Tournament(TournamentDTO tournamentDTO) {
        name = tournamentDTO.getName();
        place = tournamentDTO.getPlace();
        arbiter = tournamentDTO.getArbiter();
        roundAmount = tournamentDTO.getRoundAmount();
        playedRounds = tournamentDTO.getCurrentRound();
        begin = tournamentDTO.getBegin();
        end = tournamentDTO.getEnd();
        system = tournamentDTO.getSystem();
        rounds = new ArrayList<>();

        players = new ArrayList<>();
        for (PlayerDTO playerDTO : tournamentDTO.getPlayers()) {
            players.add(new Player(playerDTO));
        }
    }

    public Round getRound(int roundIndex) throws NoSuchRoundException {
        if (rounds != null && roundIndex >= 0 && roundIndex < rounds.size() && roundIndex < roundAmount)
            return rounds.get(roundIndex);
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

    public int getPlayedRounds() {
        return playedRounds;
    }

    public void setPlayedRounds(int playedRounds) {
        this.playedRounds = playedRounds;
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

    public void setCurrentRoundPlayed() {
        playedRounds++;
    }
}
