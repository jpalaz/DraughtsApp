package by.draughts.service;

import by.draughts.model.game.Position;
import by.draughts.model.ply.PlyPosition;

import java.util.List;

public interface PositionService {
    List<PlyPosition> getPossibleMoves(Position obj);
}
