package by.draughts.service.impl;

import by.draughts.model.game.Game;
import by.draughts.service.GameService;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {
    /*@Autowired
    private */ // TODO: Repository - Hibernate

    @Override
    public Game getGameById(String id) {
        return null;
    }
}
