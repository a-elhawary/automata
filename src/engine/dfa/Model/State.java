package engine.dfa.Model;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
public class State {
    private int StateID;
    private Map<String, HashSet<State>> Transitions;

    public State(int stateID){
        this.StateID=stateID;
        Transitions =new HashMap<>();
    }
    public void enterState(String key, State value){
        HashSet<State> TransitionSet;
        if(Transitions.containsKey(key))
            TransitionSet=Transitions.get(key);
        else
            TransitionSet = new HashSet<>();
        TransitionSet.add(value);
        Transitions.put(key,TransitionSet);
    }
    @Override
    public String toString(){
        return "State" + StateID;
    }

    public int getStateID() {
        return StateID;
    }

    public Map<String, HashSet<State>> getTransitions() {
        return this.Transitions;
    }
}
