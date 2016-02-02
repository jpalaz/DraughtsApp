package by.draughts.model.ply;

public class Comment {
    private String commentBefore;
    private String commentAfter;
    private PlyRate rate;

    public Comment() {
    }

    public String getCommentBefore() {
        return commentBefore;
    }

    public void setCommentBefore(String commentBefore) {
        this.commentBefore = commentBefore;
    }

    public String getCommentAfter() {
        return commentAfter;
    }

    public void setCommentAfter(String commentAfter) {
        this.commentAfter = commentAfter;
    }

    public PlyRate getRate() {
        return rate;
    }

    public void setRate(PlyRate rate) {
        this.rate = rate;
    }
}
