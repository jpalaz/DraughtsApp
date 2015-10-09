package by.draughts.controller;

import by.draughts.model.Game;
import by.draughts.model.GameResult;
import by.draughts.model.Ply;
import by.draughts.model.Position;
import by.draughts.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameService gameService;

    private static Game game;

    static {
        game = new Game();
        game.setBlack("Black");
        game.setWhite("White");
        game.setDate(new Date());
        game.setEvent("Belarus Highest League 2015");
        game.setGameType(25);
        game.setId("1");
        game.setResult(GameResult.BLACK_WON);
        game.setSite("Minsk");
        game.setBeginPosition(new Position());
        game.setRound(1);

        Ply first = new Ply();
        game.setFirstMove(first);

        first.setFrom(22);
        first.setTo(18);
        first.setPosition(new Position());
        first.setNumber(1);

        ArrayList<Integer> draughts = new ArrayList<>();
        for (int i = 21; i <= 32; ++i) {
            draughts.add(i);
        }
        game.getBeginPosition().setWhites(draughts);

        draughts = new ArrayList<>(draughts);
        draughts.set(1, 18);
        first.getPosition().setWhites(draughts);

        draughts = new ArrayList<>();
        for (int i = 1; i <= 12; ++i) {
            draughts.add(i);
        }
        game.getBeginPosition().setBlacks(draughts);

        first.getPosition().setBlacks(new ArrayList<>(draughts));
    }

    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    public Game getGame(@PathVariable String gameId) {
        return game;
        //return gameService.getGameById(gameId);
    }

    @RequestMapping(value = "/{gameId}/moves", method = RequestMethod.GET)
    public String getMoves(@PathVariable String gameId) {
        return game.generateMoves().toString();
        //return gameService.getGameById(gameId);
    }
}
