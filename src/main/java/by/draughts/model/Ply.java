package by.draughts.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Ply {
    private int number;
    private int from;
    private int to;
    private boolean whiteSide;
    private boolean beat;
    private Position position;
    private String commentBefore;
    private String commentAfter;
    private PlyRate rate;
    private Ply mainResponse;
    private List<Ply> alternatives;

    public Ply() {
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isWhiteSide() {
        return whiteSide;
    }

    public void setWhiteSide(boolean whiteSide) {
        this.whiteSide = whiteSide;
    }

    public boolean isBeat() {
        return beat;
    }

    public void setBeat(boolean beat) {
        this.beat = beat;
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

    public Ply getMainResponse() {
        return mainResponse;
    }

    public void setMainResponse(Ply mainResponse) {
        this.mainResponse = mainResponse;
    }

    public List<Ply> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Ply> alternatives) {
        this.alternatives = alternatives;
    }

/*    ply.append(", \"from\":");
    ply.append(from);
    ply.append(", \"to\":");
    ply.append(to);
    ply.append(", \"whiteSide\":");
    ply.append(whiteSide);
    ply.append(", \"position\":");
    ply.append(position);
    ply.append(", \"commentBefore\":");
    ply.append(commentBefore);
    ply.append(", \"commentAfter\":");
    ply.append(commentAfter);
    ply.append(", \"rate\":");*/

    public void addToMovesJson(JSONArray movesLine) {
        JSONObject ply = new JSONObject();
        ply.put("index", number)
                .put("from", from)
                .put("to", to)
                .put("whiteSide", whiteSide)
                .put("position", position)
                .put("commentBefore", commentBefore);
        ply.put("commentAfter", commentAfter);
        ply.put("rate", rate);

        if (alternatives != null) {
            JSONArray alternativesJson = new JSONArray();
            for (Ply alter : alternatives) {
                JSONArray alterJson = new JSONArray();
                alter.addToMovesJson(alterJson);
            }
            ply.append("alternatives", alternativesJson);
        }

        movesLine.put(ply);

        if (mainResponse != null) {
            mainResponse.addToMovesJson(movesLine);
        }
    }
}
