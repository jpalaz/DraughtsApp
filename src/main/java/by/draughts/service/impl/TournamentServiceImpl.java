package by.draughts.service.impl;

import by.draughts.dto.tournament.GameTitleDTO;
import by.draughts.model.exception.NoSuchRoundException;
import by.draughts.model.game.GameTitle;
import by.draughts.model.person.Player;
import by.draughts.model.tournament.Result;
import by.draughts.model.tournament.Round;
import by.draughts.model.tournament.Tournament;
import by.draughts.service.TournamentService;
import by.draughts.service.utils.RoundRobinGenerator;
import by.draughts.service.utils.SwissGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {
    @Override
    public Tournament getTournamentById(String id) {
        return null;
    }

    public List<GameTitle> generateRoundRoundGames(Tournament tournament) {
        return RoundRobinGenerator.generateRounds(tournament);
    }

    @Override
    public Round getNextRound(Tournament tournament) {
        try {
            return tournament.getRound(tournament.getPlayedRounds());
        } catch (NoSuchRoundException e) {
            return generateNextRound(tournament);
        }
    }

    private Round generateNextRound(Tournament tournament) {
        List<GameTitle> games = new ArrayList<>();
        int roundNumber = 1;
        if (tournament.getRounds() != null)
            roundNumber = tournament.getRounds().size() + 1;
        Round round = new Round(new Date(), games, roundNumber);
        tournament.getRounds().add(round);
        switch (tournament.getSystem()) {
            case SWISS:
                generateNextRoundGamesSwiss(tournament);
                break;
            case ROUND_ROBIN:
                generateNextRoundGamesRound(tournament);
                break;
        }
        return round;
    }

    public List<GameTitle> generateNextRoundGamesRound(Tournament tournament) {
        return null;
    }

    @Override
    public List<GameTitle> generateNextRoundGamesSwiss(Tournament tournament) {
        return SwissGenerator.generateNextRound(tournament);
    }

    @Override
    public void setRoundResults(Tournament tournament, List<GameTitleDTO> games) {
        try {
            Round currentRound = tournament.getRound(tournament.getPlayedRounds());
            currentRound.setCurrentGamesResults(games);
            tournament.setCurrentRoundPlayed();
        } catch (NoSuchRoundException e) {
            e.printStackTrace(); // TODO: implement handling properly!
        }
//        return null;
    }
}
