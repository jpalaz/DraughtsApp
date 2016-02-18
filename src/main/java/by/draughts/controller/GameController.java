package by.draughts.controller;

import by.draughts.model.game.*;
import by.draughts.model.ply.*;
import by.draughts.model.person.Player;
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

        GameTitle title = new GameTitle(new Player("I'm white and fluffy"), new Player("Black Sirius"));
        game.setTitle(title);

        game.setGameType(25);
        game.setId("1");
        game.setBegin(new Position());

        ArrayList<Draught> draughts = new ArrayList<>();
        game.getBegin().setDraughts(draughts);
        game.getBegin().setIsWhiteMove(true);

        for (int i = 21; i <= 32; ++i) {
            draughts.add(new Draught(true, false, false, i));
        }
        for (int i = 1; i <= 12; ++i) {
            draughts.add(new Draught(false, false, false, i));
        }

        Ply ply = new Ply(new PlyPosition());
        game.setMainMoves(new Sequence());
        game.getMainMoves().add(ply);
        setFirstPly(ply);

        ply = new Ply(new PlyPosition());
        setSecondPly(ply);
        game.getMainMoves().add(ply);

        ply = new Ply(new PlyPosition());
        setThirdPly(ply);
        game.getMainMoves().add(ply);
    }

    private static void setFirstPly(Ply ply) {
        ply.getPlyPosition().setFrom((byte)22);
        ply.getPlyPosition().setTo((byte)18);
        ply.getPlyPosition().setPosition(new Position());
        ply.setNumber((byte)1);

        ply.setComment(new Comment());
        ply.getComment().setCommentBefore("Comment before first ply");
        ply.getComment().setCommentAfter("Comment after first ply");
        ply.getComment().setRate(PlyRate.GOOD);

        ArrayList<Draught> draughts = new ArrayList<>();
        ply.getPlyPosition().getPosition().setDraughts(draughts);
        ply.getPlyPosition().getPosition().setIsWhiteMove(false);

        for (int i = 21; i <= 32; ++i) {
            draughts.add(new Draught(true, false, false, i));
        }
        for (int i = 1; i <= 12; ++i) {
            draughts.add(new Draught(false, false, false, i));
        }

        draughts.get(1).setPosition(18);
    }

    private static void setSecondPly(Ply ply) {
        ply.getPlyPosition().setFrom((byte)11);
        ply.getPlyPosition().setTo((byte)16);
        ply.getPlyPosition().setPosition(new Position());
        ply.setNumber((byte)1);

        ply.setComment(new Comment());
        ply.getComment().setCommentBefore("before second ply");
        ply.getComment().setCommentAfter("after second ply");
        ply.getComment().setRate(PlyRate.DISPUTABLE);

        ArrayList<Draught> draughts = new ArrayList<>();
        ply.getPlyPosition().getPosition().setDraughts(draughts);
        ply.getPlyPosition().getPosition().setIsWhiteMove(true);

        for (int i = 21; i <= 32; ++i) {
            draughts.add(new Draught(true, false, false, i));
        }
        for (int i = 1; i <= 12; ++i) {
            draughts.add(new Draught(false, false, false, i));
        }

        draughts.get(1).setPosition(18);
        draughts.get(22).setPosition(16);
    }

    private static void setThirdPly(Ply ply) {
        ply.getPlyPosition().setFrom((byte)24);
        ply.getPlyPosition().setTo((byte)19);
        ply.getPlyPosition().setPosition(new Position());
        ply.setNumber(2);

        ply.setComment(new Comment());
        ply.getComment().setCommentBefore("before third ply");
        ply.getComment().setCommentAfter("after third ply");
        ply.getComment().setRate(PlyRate.GOOD);

        ArrayList<Draught> draughts = new ArrayList<>();
        ply.getPlyPosition().getPosition().setDraughts(draughts);
        ply.getPlyPosition().getPosition().setIsWhiteMove(false);

        for (int i = 21; i <= 32; ++i) {
            draughts.add(new Draught(true, false, false, i));
        }
        for (int i = 1; i <= 12; ++i) {
            draughts.add(new Draught(false, false, false, i));
        }

        draughts.get(1).setPosition(18);
        draughts.get(3).setPosition(19);
        draughts.get(22).setPosition(16);
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
