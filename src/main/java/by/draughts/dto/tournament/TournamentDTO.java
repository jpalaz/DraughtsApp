package by.draughts.dto.tournament;

import by.draughts.model.person.Player;
import by.draughts.model.tournament.Tournament;
import by.draughts.model.tournament.TournamentSystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TournamentDTO {
    private String name;
    private String place;
    private String arbiter;
    private int roundAmount;
    private int currentRound;
    private Date begin;
    private Date end;

    private TournamentSystem system;
    private List<PlayerDTO> players;

    @Override
    public String toString() {
        return "TournamentDTO{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", arbiter='" + arbiter + '\'' +
                ", roundAmount=" + roundAmount +
                ", currentRound=" + currentRound +
                ", begin=" + begin +
                ", end=" + end +
                ", system=" + system +
                ", players=" + players +
                '}';
    }

    public TournamentDTO() {
    }

    public TournamentDTO(Tournament tournament) {
        name = tournament.getName();
        place = tournament.getPlace();
        arbiter = tournament.getArbiter();//new PersonDTO(tournament.getArbiter());
        roundAmount = tournament.getRoundAmount();
        currentRound = tournament.getPlayedRounds();
        begin = tournament.getBegin();
        end = tournament.getEnd();
        system = tournament.getSystem();

        players = new ArrayList<>(tournament.getPlayers().size());
        for (Player player : tournament.getPlayers()) {
            players.add(new PlayerDTO(player));
        }
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

    public TournamentSystem getSystem() {
        return system;
    }

    public void setSystem(TournamentSystem system) {
        this.system = system;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }
}
