package engine.pda;
import java.util.ArrayList;

public class PDAutomata {
    static final char EPSILON = 0;
    private ArrayList<Character> language;
    private ArrayList<String> states;
    private int startState = -1;
    private ArrayList<Integer> finalStates;
    private ArrayList<PDATransition> transitions;

    public PDAutomata(){
        this.language = new ArrayList<>();
        this.states = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.transitions = new ArrayList<>();
    }

    public void setLanguage(ArrayList<Character> language){
        this.language = language;
    }

    public void addCharToLang(char alphabet){
        this.language.add(alphabet);
    }

    public void setStates(ArrayList<String> states){
        this.states = states;
    }

    public void addState(String state){
        this.states.add(state);
    }

    private int findStateIndex(String state){
        for(int i = 0; i < states.size(); i++){
            if(states.get(i) == state){
                return i;
            }
        }
        return -1;
    }

    // returns true if start state is found, false if not exists
    public boolean setStartState(String state){
        int startIndex = this.findStateIndex(state);
        if(startIndex == -1){
            return false;
        }
        this.startState = startIndex;
        return true;
    }

    // returns true if start state is found, false if not exists
    public boolean addFinalState(String state) {
        int finalIndex = this.findStateIndex(state);
        if (finalIndex == -1) {
            return false;
        }
        this.finalStates.add(finalIndex);
        return true;
    }

    public boolean addTransition(String fromState, String toState, char onInput, String oldStack, String newStack){
        int startIndex = this.findStateIndex(fromState);
        if(startIndex == -1) return false;

        PDATransition transition = new PDATransition(startIndex, onInput, toState,oldStack, newStack);

        boolean added = false;

        for(int i = 0; i < this.transitions.size(); i++){
            boolean largerState = this.transitions.get(i).fromState > transition.fromState;
            boolean sameState = this.transitions.get(i).fromState == transition.fromState;
            boolean largerChar = this.transitions.get(i).character > transition.character;
            if(largerState || (sameState && largerChar)){
                this.transitions.add(i, transition);
                added = true;
                break;
            }
        }

        if(!added) {
            this.transitions.add(transition);
        }

        // if character was new add it to language
        boolean newCharacter = true;
        for(int i = 0; i < language.size(); i++){
            if(language.get(i) == onInput){
                newCharacter = false;
            }
        }
        if(newCharacter) this.addCharToLang(onInput);
        return true;
    };

    public String toString(){
        String result = "";
        result += "// Push Down Automata\n";
        result += "Start State: " + this.states.get(this.startState) + ",\n";
        result += "States: {\n";
        for(int i = 0; i < this.states.size(); i++) {
            result += this.states.get(i);
            if (i != this.states.size() - 1) {
                result += ",\n";
            }
        }
        result += "\n},\n";
        result += "Final States: {\n";
        for(int i = 0; i < this.finalStates.size(); i++){
            result += this.states.get(this.finalStates.get(i));
            if (i != this.finalStates.size() - 1) {
                result += ",\n";
            }
        }
        result += "\n}\nTransitions: [\n";
        for(int i = 0; i < this.transitions.size(); i++){
            result += "Transition: {\n";
            result += "fromState: " + this.states.get(this.transitions.get(i).fromState) + ",\n";
            result += "onChar: " + this.transitions.get(i).character + ",\n";
            result += "toStates: " + this.transitions.get(i).toStates + ",\n";
            result += "pop: " + this.transitions.get(i).oldStack + "\n";
            result += "push: " + this.transitions.get(i).newStack + "\n";
            if (i != this.transitions.size() - 1) {
                result += "\n},\n";
            }else{
                result += "\n}\n";
            }
        }
        result += "\n]\n";
        return result;
    }

}

