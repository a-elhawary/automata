package engine.dfa.Model;
import java.util.ArrayList;

public class FinalStates extends ArrayList<State> {
    private int grpID;
    public FinalStates(int id){
        grpID=id;
    }
    FinalStates(int id, FinalStates f)
    {
        grpID=id;
        for (State s : f)
            add(s);
    }
@Override
    public boolean add(State state){
        if(!this.contains(state))
            return super.add(state);
    return false;
}
    @Override
    public String toString() {
        return "Group" + grpID + "\t" + super.toString();
    }

    public int getGrpID() {
        return grpID;
    }

    public void setGrpID(int grpID) {
        this.grpID = grpID;
    }

}

