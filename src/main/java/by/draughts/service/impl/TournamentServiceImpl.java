package by.draughts.service.impl;

import by.draughts.model.exception.NoSuchRoundException;
import by.draughts.model.game.Game;
import by.draughts.model.game.GameTitle;
import by.draughts.model.tournament.Player;
import by.draughts.model.tournament.Round;
import by.draughts.model.tournament.Tournament;
import by.draughts.service.TournamentService;
import by.draughts.service.utils.RoundRobinGenerator;
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
    public List<GameTitle> getNextRoundGames(Tournament tournament) {
        try {
            return tournament.getRoundGames(tournament.getCurrentRound());
        } catch (NoSuchRoundException e) {
            return generateNextRound(tournament);
        }
    }

    private List<GameTitle> generateNextRound(Tournament tournament) {
        List<GameTitle> games = new ArrayList<>();
        switch (tournament.getSystem()) {
            case SWISS:
                generateNextRoundGamesSwiss(tournament);
                break;
            case ROUND_ROBIN:
                generateNextRoundGamesRound(tournament);
                break;
        }
        return games;
    }

    public List<GameTitle> generateNextRoundGamesRound(Tournament tournament) {
        return null;
    }

    @Override
    public List<GameTitle> generateNextRoundGamesSwiss(Tournament tournament) {
        return null;
    }
}
