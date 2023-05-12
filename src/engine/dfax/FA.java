package engine.dfax;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FA {
    int stateCount = 0;
    int startState = 0;
    ArrayList<Integer> states;
    ArrayList<FATransition> transitions;
    ArrayList<Integer> finalStates;

    public FA(){
        states = new ArrayList<>();
        transitions = new ArrayList<>();
        finalStates = new ArrayList<>();
    }

    public void addState(int state){
        states.add(state);
        stateCount += 1;
    }

    public void addFinalState(int state){
        finalStates.add(state);
    }

    public void addStartState(int state){
        startState = state;
    }

    public void addTransition(int startState, char input, int endState){
        transitions.add(new FATransition(startState,input, endState));
    }

    public void saveToFile(){
        String nfa = "";
        nfa += String.valueOf(stateCount);
        nfa += "\n";
        nfa += "start " + startState;
        nfa += "\n";
        for(int i = 0; i < transitions.size(); i++){
            nfa += transitions.get(i).startState;
            nfa += "";
            nfa += transitions.get(i).input;
            nfa += "";
            nfa += transitions.get(i).endState;
            nfa += "\n";
        }
        for(int i = 0; i < finalStates.size(); i++){
            nfa += "final " + finalStates.get(i);
            nfa += "\n";
        }
        storeInDFAfile(nfa);
    }

    private void storeInDFAfile(String dfaString) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter("nfa"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        pw.println(dfaString);
        pw.close();
    }
}

class FATransition{
    int startState;
    int endState;
    char input;

    public FATransition(int startState, char input, int endState){
        this.startState = startState;
        this.endState = endState;
        this.input = input;
    }
}
