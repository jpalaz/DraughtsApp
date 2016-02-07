package by.draughts.service;

import by.draughts.model.game.GameTitle;
import by.draughts.model.tournament.Round;
import by.draughts.model.tournament.Tournament;

import java.util.List;

public interface TournamentService {

    Tournament getTournamentById(String id);

    Round getNextRound(Tournament tournament);

    List<GameTitle> generateRoundRoundGames(Tournament tournament);

    List<GameTitle> generateNextRoundGamesSwiss(Tournament tournament);

    void setRoundResults(Tournament tournament, List<GameTitle> games);
}
