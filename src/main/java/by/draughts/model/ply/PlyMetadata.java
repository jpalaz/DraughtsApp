package by.draughts.model.ply;

import java.util.ArrayList;
import java.util.List;

public class PlyMetadata{
    private byte from;
    private byte to;
    private List<Byte> intermediats;
    private boolean isBeat;

    public PlyMetadata(){
        isBeat = false;
    }

    public PlyMetadata(PlyMetadata obj){
        from = obj.from;
        to = obj.to;
        isBeat = obj.isBeat;
        if(obj.intermediats != null){
            intermediats = new ArrayList<>( obj.intermediats );
        }
    }

    public List<Byte> getIntermediats(){
        return intermediats;
    }

    public void setIntermediats(List<Byte> intermediats){
        this.intermediats = intermediats;
    }

    public void addIntermediat(byte intermediat){
        intermediats.add( intermediat );
    }

    public void setIsBeat(boolean isBeat){
        this.isBeat = isBeat;
    }

    public byte getFrom(){
        return from;
    }

    public void setFrom(byte from){
        this.from = from;
    }

    public byte getTo(){
        return to;
    }

    public void setTo(byte to){
        this.to = to;
    }

    public boolean isBeat(){
        return isBeat;
    }

    public void setBeat(boolean beat){
        this.isBeat = beat;
    }
}
