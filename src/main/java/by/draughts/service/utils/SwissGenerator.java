package by.draughts.service.utils;

import by.draughts.model.game.GameTitle;
import by.draughts.model.person.Player;
import by.draughts.model.tournament.Round;
import by.draughts.model.tournament.Tournament;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwissGenerator {
//    private static int gamesAmount;

    public static List<GameTitle> generateNextRound(Tournament tournament) {
        if (tournament.getRounds() == null || tournament.getRounds().size() == 0) {
            return generateFirstRound(tournament);
        }
        return new ArrayList<>();
//        return rounds.get(tournament.getPlayedRounds()).getGames();
    }

    private static List<GameTitle> generateFirstRound(Tournament tournament) {
        List<Player> players = tournament.getPlayers();
        Collections.sort(players, Player.getSortingComparator());

        List<Round> rounds = new ArrayList<>();
        tournament.setRounds(rounds);
        if (players.size() % 2 != 0) {
            players.add(new Player("Free"));
        }
        int gamesAmount = players.size() / 2;
        List<GameTitle> games = new ArrayList<>();
        rounds.add(new Round(tournament.getBegin(), games, 1));
        boolean isFirstWhite = true;
        for (int i = 0; i < gamesAmount; ++i, isFirstWhite = !isFirstWhite) {
            if (isFirstWhite) {
                games.add(new GameTitle(players.get(i), players.get(i + gamesAmount)));
            } else {
                games.add(new GameTitle(players.get(i + gamesAmount), players.get(i)));
            }
        }
        return games;
    }

}
