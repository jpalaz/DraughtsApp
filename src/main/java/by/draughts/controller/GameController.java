package by.draughts.controller;

import by.draughts.model.game.*;
import by.draughts.model.ply.*;
import by.draughts.model.tournament.Player;
import by.draughts.service.GameService;
import by.draughts.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameService gameService;

    @Autowired
    private PositionService positionService;

    private static Game game;

    static {
        game = new Game();
        GameMetadata metadata = new GameMetadata();
        metadata.setDate(new Date());
        metadata.setEvent("Belarus Highest League 2015");
        metadata.setSite("Minsk");
        metadata.setRound(1);
        game.setMetadata(metadata);

        GameTitle title = new GameTitle(new Player("I'm white and fluffy"), new Player("Black Sirius"));
        game.setTitle(title);

        game.setGameType(25);
        game.setId("1");
        game.setBegin(new Position());

        Ply ply = new Ply(new PlyMetadata());
        game.setMainMoves(new Sequence());
        game.getMainMoves().add(ply);
        setFirstPly(ply);

        ply = new Ply(new PlyMetadata());
        setSecondPly(ply);
        game.getMainMoves().add(ply);

        ply = new Ply(new PlyMetadata());
        setThirdPly(ply);
        game.getMainMoves().add(ply);
    }

    private static void setFirstPly(Ply ply) {
        ply.getMetadata().setFrom((byte)22);
        ply.getMetadata().setTo((byte)18);
        ply.setPosition(new Position());
        ply.getMetadata().setNumber((byte)1);
        ply.getMetadata().setWhiteSide(true);

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
        draughts.set(1, (byte)18);
        ply.getPosition().setWhites(draughts);

        draughts = new ArrayList<>();
        for (byte i = 1; i <= 12; ++i) {
            draughts.add(i);
        }
        game.getBegin().setBlacks(draughts);

        ply.getPosition().setBlacks(new ArrayList<>(draughts));

    }

    private static void setSecondPly(Ply ply) {
        ply.getMetadata().setFrom((byte)11);
        ply.getMetadata().setTo((byte)16);
        ply.setPosition(new Position());
        ply.getMetadata().setNumber((byte)1);
        ply.getMetadata().setWhiteSide(false);

        ply.setComment(new Comment());
        ply.getComment().setCommentBefore("before second ply");
        ply.getComment().setCommentAfter("after second ply");
        ply.getComment().setRate(PlyRate.DISPUTABLE);

        ArrayList<Byte> draughts = new ArrayList<>();
        for (byte i = 21; i <= 32; ++i) {
            draughts.add(i);
        }
        draughts.set(1, (byte)18);
        ply.getPosition().setWhites(draughts);

        draughts = new ArrayList<>();
        for (byte i = 1; i <= 12; ++i) {
            draughts.add(i);
        }
        draughts.set(10, (byte)16);
        ply.getPosition().setBlacks(new ArrayList<>(draughts));
    }

    private static void setThirdPly(Ply ply) {
        ply.getMetadata().setFrom((byte)24);
        ply.getMetadata().setTo((byte)19);
        ply.setPosition(new Position());
        ply.getMetadata().setNumber((byte)2);
        ply.getMetadata().setWhiteSide(true);

        ply.setComment(new Comment());
        ply.getComment().setCommentBefore("before third ply");
        ply.getComment().setCommentAfter("after third ply");
        ply.getComment().setRate(PlyRate.GOOD);

        ArrayList<Byte> draughts = new ArrayList<>();
        for (byte i = 21; i <= 32; ++i) {
            draughts.add(i);
        }
        draughts.set(1, (byte)18);
        draughts.set(3, (byte)19);
        ply.getPosition().setWhites(draughts);

        draughts = new ArrayList<>();
        for (byte i = 1; i <= 12; ++i) {
            draughts.add(i);
        }
        draughts.set(10, (byte)16);
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
