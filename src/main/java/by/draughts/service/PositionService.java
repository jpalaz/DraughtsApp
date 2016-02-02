package by.draughts.service;

import by.draughts.model.PlyMetadata;
import by.draughts.model.Position;

import java.util.List;

public interface PositionService{
    List<PlyMetadata> getPosibleMoves(Position obj);
}
