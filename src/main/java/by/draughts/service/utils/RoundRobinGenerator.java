package by.draughts.service.utils;

import by.draughts.model.game.GameTitle;
import by.draughts.model.person.Player;
import by.draughts.model.tournament.Result;
import by.draughts.model.tournament.Round;
import by.draughts.model.tournament.Tournament;

import java.util.ArrayList;
import java.util.List;

public class RoundRobinGenerator {
    private static List<Round> rounds;
    private static List<Player> players;
    private static boolean isWhiteSideForLast;
    private static int gamesAmount;
    private static Player last;
    private static int increase;
    private static int decrease;

    public static List<GameTitle> generateRounds(Tournament tournament) {
        setParameters(tournament);
        generate(tournament);
        return rounds.get(tournament.getPlayedRounds()).getGames();
    }

    private static void setParameters(Tournament tournament) {
        rounds = new ArrayList<>(tournament.getRoundAmount());
        players = tournament.getPlayers();
        isWhiteSideForLast = false;
        if (players.size() % 2 != 0) {
            last = new Player("Free");
            players.add(last);
        } else {
            last = players.get(players.size() - 1);
        }
        gamesAmount = players.size() / 2;
        for (Player player : players) {
            player.setResults(players.size() - 1);
        }
    }

    private static void generate(Tournament tournament) {
        int lastIndex = gamesAmount * 2 - 1;
        increase = 0;
        decrease = lastIndex - 1;
        for (int i = 0; i < tournament.getRoundAmount(); ++i) {
            Round round = new Round(tournament.getBegin(), getGames(lastIndex), i + 1);
            rounds.add(round);
        }
        tournament.setRounds(rounds);
    }

    private static List<GameTitle> getGames(int lastIndex) {
        List<GameTitle> roundGames = setFirstGame(lastIndex);
        return setRestGames(lastIndex, roundGames);
    }

    private static List<GameTitle> setFirstGame(int lastIndex) {
        List<GameTitle> roundGames = new ArrayList<>();
        increase = getIncrease(lastIndex);
        GameTitle game = getFirstGameTitle(players.get(increase), last, isWhiteSideForLast);
        roundGames.add(game);
        increase++;
        isWhiteSideForLast = !isWhiteSideForLast;
        return roundGames;
    }

    private static GameTitle getFirstGameTitle(Player increased, Player last, boolean isWhiteSideForLast) {
        GameTitle game;
        if (isWhiteSideForLast) {
            game = new GameTitle(last, increased);
        } else {
            game = new GameTitle(increased, last);
        }
        return game;
    }

    private static List<GameTitle> setRestGames(int lastIndex, List<GameTitle> roundGames) {
        for (int i = 1; i < gamesAmount; ++i, ++increase, --decrease) {
            increase = getIncrease(lastIndex);
            decrease = getDecrease(lastIndex);
            roundGames.add(new GameTitle(players.get(increase), players.get(decrease)));
        }
        return roundGames;
    }

    private static int getIncrease(int lastIndex) {
        return (increase == lastIndex) ? 0 : increase;
    }

    private static int getDecrease(int lastIndex) {
        return (decrease == -1) ? (lastIndex - 1) : decrease;
    }
}
