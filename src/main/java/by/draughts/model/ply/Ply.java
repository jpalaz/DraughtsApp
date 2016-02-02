package by.draughts.model.ply;

import by.draughts.model.game.Position;

import java.util.List;

public class Ply {
    private short number;
    private PlyMetadata metadata;
    private Position position;
    private Comment comment;
    private List<Sequence> alternatives;

    public Ply() {
    }

    public Ply(PlyMetadata metadata) {
        this.metadata = metadata;
    }

    public short getNumber(){
        return number;
    }

    public void setNumber(short number){
        this.number = number;
    }

    public PlyMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(PlyMetadata metadata) {
        this.metadata = metadata;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Sequence> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Sequence> alternatives) {
        this.alternatives = alternatives;
    }
}
