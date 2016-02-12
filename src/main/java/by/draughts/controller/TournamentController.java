package by.draughts.controller;

import by.draughts.dto.game.GameTitleDTO;
import by.draughts.dto.tournament.TournamentDTO;
import by.draughts.model.game.GameTitle;
import by.draughts.model.person.Player;
import by.draughts.model.tournament.Round;
import by.draughts.model.tournament.Tournament;
import by.draughts.model.tournament.TournamentSystem;
import by.draughts.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    private static Tournament tournamentRR = new Tournament();
    private static Tournament tournamentSwiss = new Tournament();

    static {
        tournamentRR.setName("World Championship 2016");
        tournamentRR.setPlace("Belarus, Minsk");
        tournamentRR.setArbiter("Nosevich");
        tournamentRR.setSystem(TournamentSystem.ROUND_ROBIN);
        tournamentRR.setBegin(new Date());
        tournamentRR.setEnd(addDays(2));

        List<Player> players = new ArrayList<>();
        tournamentRR.setPlayers(players);
        players.add(new Player("1"));
        players.add(new Player("2"));
        players.add(new Player("3"));
        players.add(new Player("4"));
        players.add(new Player("5"));
        players.add(new Player("6"));
        players.add(new Player("7"));
        tournamentRR.setRoundAmount(7);
        tournamentRR.setPlayedRounds(0);
    }

    static {
        tournamentSwiss.setName("Europe Championship 2016");
        tournamentSwiss.setPlace("Belarus, Hrodna");
        tournamentSwiss.setArbiter("Aniska");
        tournamentSwiss.setSystem(TournamentSystem.SWISS);
        tournamentSwiss.setBegin(addDays(4));
        tournamentSwiss.setEnd(addDays(7));
        tournamentSwiss.setPlayers(tournamentRR.getPlayers());
        tournamentSwiss.setRoundAmount(5);
        tournamentSwiss.setPlayedRounds(0);
    }

    private static Date addDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    @RequestMapping(value = "/robin", method = RequestMethod.GET)
    public Tournament getTournamentRobin() {
        tournamentService.generateRoundRoundGames(tournamentRR);
        return tournamentRR;
    }

    @RequestMapping(value = "/swiss", method = RequestMethod.GET)
    public Tournament getTournamentSwiss() {
        tournamentService.generateNextRoundGamesSwiss(tournamentSwiss);
        return tournamentSwiss;
    }

    @RequestMapping(value = "/rounds", method = RequestMethod.GET)
    public List<Round> getGames() {
        return tournamentRR.getRounds();
    }

    @RequestMapping(value = "/robin/next_round", method = RequestMethod.GET)
    public Round getTournamentRobinNextRound() {
        if (tournamentRR.getPlayedRounds() == tournamentRR.getRoundAmount()) {
            tournamentRR.setPlayedRounds(0);
        }
        Round round = tournamentService.getNextRound(tournamentRR);
        return round;
    }

    @RequestMapping(value = "/swiss/next_round", method = RequestMethod.GET)
    public Round getTournamentSwissNextRound() {
        return tournamentService.getNextRound(tournamentSwiss);
     }

    @RequestMapping(value = "/robin/players", method = RequestMethod.GET)
    public List<Player> getTournamentRobinPlayers() {
        return tournamentRR.getPlayers();
    }

    @RequestMapping(value = "/swiss/players", method = RequestMethod.GET)
    public List<Player> getTournamentSwissPlayers() {
        return tournamentSwiss.getPlayers();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createTournament(@RequestBody TournamentDTO tournamentDTO) {
        if (tournamentDTO.getSystem() == TournamentSystem.SWISS)
            tournamentSwiss = new Tournament(tournamentDTO);
        else
            tournamentRR = new Tournament(tournamentDTO);
    }

    @RequestMapping(value = "/current_round", method = RequestMethod.POST)
    public void setRoundResultsSwiss(@RequestBody List<GameTitle> games) {
        System.out.println(games);
        tournamentService.setRoundResults(tournamentSwiss, games);
    }

    @RequestMapping(value = "/current_round/robin", method = RequestMethod.POST)
    public void setRoundResultsRound(@RequestBody List<GameTitle> games) {
        tournamentService.setRoundResults(tournamentRR, games);
    }
}