package by.draughts.model.ply;

import java.util.List;

public class Ply {
    private int number;
    private PlyPosition metadata;
    private Comment comment;
    private List<Sequence> alternatives;

    public Ply() {
    }

    public Ply(PlyPosition metadata) {
        this.metadata = metadata;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public PlyPosition getPlyPosition() {
        return metadata;
    }

    public void setPlyPosition(PlyPosition metadata) {
        this.metadata = metadata;
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
