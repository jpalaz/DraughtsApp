package by.draughts.controller;

import by.draughts.model.game.Position;
import by.draughts.model.ply.PlyPosition;
import by.draughts.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @RequestMapping(method = RequestMethod.POST)
    public List<PlyPosition> getPossibleMoves(@RequestBody Position position) {
        return positionService.getPossibleMoves(position);
    }

    @RequestMapping(value = "new_ply", method = RequestMethod.POST)
    public List<PlyPosition> selectPly(@RequestBody Position position) {
        return positionService.getPossibleMoves(position);
    }
}
