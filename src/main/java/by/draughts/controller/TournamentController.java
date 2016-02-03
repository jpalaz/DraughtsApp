package by.draughts.controller;

import by.draughts.dto.tournament.TournamentDTO;
import by.draughts.model.game.Game;
import by.draughts.model.game.GameTitle;
import by.draughts.model.tournament.Player;
import by.draughts.model.tournament.Round;
import by.draughts.model.tournament.Tournament;
import by.draughts.model.tournament.TournamentSystem;
import by.draughts.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    private static Tournament tournament = new Tournament();

    static {
        tournament.setArbiter("Arbiter's surname");
        tournament.setSystem(TournamentSystem.ROUND_ROBIN);
        tournament.setBegin(new Date());

        List<Player> players = new ArrayList<>();
        tournament.setPlayers(players);
        players.add(new Player("1"));
        players.add(new Player("2"));
        players.add(new Player("3"));
        players.add(new Player("4"));
        players.add(new Player("5"));
        players.add(new Player("6"));
        players.add(new Player("7"));
        tournament.setRoundAmount(7);
        tournament.setCurrentRound(0);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Tournament getTournament() {
        tournamentService.generateRoundRoundGames(tournament);
        return tournament;
    }

    @RequestMapping(value = "/next_round", method = RequestMethod.GET)
    public List<GameTitle> getTournamentNextRound() {
        tournament.setCurrentRound(tournament.getCurrentRound() + 1);
        return tournamentService.getNextRoundGames(tournament);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createTournament(@RequestBody TournamentDTO tournamentDTO) {
        System.out.println(tournamentDTO);
        Tournament tournament = new Tournament(tournamentDTO);
        return "oK!";
    }
}