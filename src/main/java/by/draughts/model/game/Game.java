package by.draughts.model.game;

import by.draughts.model.ply.Sequence;

/*@Entity
@Table(name = "games")*/
public class Game {
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO) */
    private String id;

    private GameMetadata metadata;
    private GameTitle title;
    private int gameType;
    private Position begin;
    private Sequence mainMoves;

    public Game() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(GameMetadata metadata) {
        this.metadata = metadata;
    }

    public Sequence getMainMoves() {
        return mainMoves;
    }

    public void setMainMoves(Sequence mainMoves) {
        this.mainMoves = mainMoves;
    }

    public GameTitle getTitle() {
        return title;
    }

    public void setTitle(GameTitle title) {
        this.title = title;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public Position getBegin() {
        return begin;
    }

    public void setBegin(Position begin) {
        this.begin = begin;
    }
}
