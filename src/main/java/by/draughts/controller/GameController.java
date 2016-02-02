package by.draughts.controller;

import by.draughts.model.game.Game;
import by.draughts.model.game.GameResult;
import by.draughts.model.game.GameMetadata;
import by.draughts.model.game.Position;
import by.draughts.model.ply.*;
import by.draughts.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameService gameService;

    private static Game game;

    static {
        game = new Game();
        GameMetadata metadata = new GameMetadata();
        metadata.setDate(new Date());
        metadata.setEvent("Belarus Highest League 2015");
        metadata.setSite("Minsk");
        metadata.setRound(1);

        game.setMetadata(metadata);
        game.setBlack("Black");
        game.setWhite("White");
        game.setGameType(25);
        game.setId("1");
        game.setResult(GameResult.BLACK_WON);
        game.setBegin(new Position());

        Ply ply = new Ply(new PlyMetadata());
        game.setMainMoves(new Sequence());
        game.getMainMoves().add(ply);

        ply.getMetadata().setFrom((byte)22);
        ply.getMetadata().setTo((byte)18);
        ply.setPosition(new Position());
        ply.getMetadata().setNumber((byte)1);

        ply.setComment(new Comment());
        ply.getComment().setCommentBefore("Comment before first ply");
        ply.getComment().setCommentAfter("Comment after first ply");
        ply.getComment().setRate(PlyRate.GOOD);

        ArrayList<Byte> draughts = new ArrayList<>();
        for (byte i = 21; i <= 32; ++i) {
            draughts.add(i);
        }
        game.getBegin().setWhites(draughts);

        draughts = new ArrayList<>(draughts);
        draughts.set((byte)1, (byte)18);
        ply.getPosition().setWhites(draughts);

        draughts = new ArrayList<>();
        for (byte i = 1; i <= 12; ++i) {
            draughts.add(i);
        }
        game.getBegin().setBlacks(draughts);

        ply.getPosition().setBlacks(new ArrayList<>(draughts));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Game getGame() {
        return game;
        //return gameService.getGameById(gameId);
    }

    @RequestMapping(value = "/{gameId}/moves", method = RequestMethod.GET)
    public Game getMoves(@PathVariable String gameId) {
        return game;
        //return gameService.getGameById(gameId);
    }
}
