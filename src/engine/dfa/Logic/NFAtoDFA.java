package engine.dfa.Logic;

import engine.dfa.InputHandler.Input;
import engine.dfa.Model.*;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class NFAtoDFA {

    private Input inputHandler;
    private FinalStates finalStates;
    private String dfaString;
    private String finalState;
    private int counter;
    private int startStateID;
    private boolean isFinal;
    private State dfaStates[];
    private Set<String> inputSymbols;
    private LinkedList<String> statesQueue;
    private Map<String, Integer> statesMap;
    private Map<String, HashSet<State>> tempTrans;

    public  NFAtoDFA(){
        inputHandler = new Input();
        startStateID=0;
        statesQueue=new LinkedList<>();
        statesMap=new HashMap<>();
        dfaString="";
        finalState="final";
        counter=1;
        isFinal=false;
        tempTrans=new HashMap<>();

    }

    public String convertToDfa() throws IOException{
        getInputFromNFAfile();
        initialiseDFAString();
        initialiseStatesMap();
        initialiseStatesQueue();

        while(statesQueueHasElements())
        {
            char [] states = getStateFromQueue();
            for(String inputSymb: inputSymbols)
            {
                String tempState ="";
                isFinal = false;
                for(char stateNumChar: states){
                    if(stateHasTransitionForInput( stateNumChar, inputSymb)){
                        //from char to int
                        tempTrans=dfaStates[stateNumChar-48].getTransitions();
                        for(State state: tempTrans.get(inputSymb))
                        {
                            tempState += state.getStateID();
                            if (stateIsFinal(state))
                                isFinal=true;

                        }
                    }
                }
                tempState = sortStates(tempState);
                if(statesMapDoesntContainKey(tempState))
                {
                    addToMap(tempState, counter++);
                    addToQueue(tempState);
                    if(isFinal)
                        saveStateifFinal(tempState);
                }
                addToDFAString(tempState,states,inputSymb);

            }
        }
        addFinalStateToDFAString(counter,finalState);
        System.out.println(dfaString);
        storeInDFAfile(dfaString);
        return dfaString;
    }

    private void addToDFAString(String tempState, char[] states, String inputSymb) {
        dfaString = dfaString + statesMap.get(String.valueOf(states)) + " " + inputSymb + " " + statesMap.get(tempState)
                + "\n";
    }

    private void storeInDFAfile(String dfaString) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter("dfa"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        pw.println(dfaString);
        pw.close();
    }

    private void saveStateifFinal( String tempState) {
        finalState = finalState + " " + statesMap.get(tempState);
    }

    private void addFinalStateToDFAString(int stateCounter, String finalState) {
        dfaString = stateCounter + "\n" + dfaString;
        dfaString = dfaString + finalState + "\n";
    }

    private void addToMap(String tempState, int stateNum) {
        statesMap.put(tempState,stateNum);
    }

    private void addToQueue(String tempState) {
        statesQueue.add(tempState);
    }

    private boolean statesMapDoesntContainKey(String key) {
        if(statesMap.containsKey(key))
            return false;
        return true;
    }

    private String sortStates(String tempState) {
        char [] newStates = tempState.toCharArray();
        Arrays.sort(newStates);
        return new String(newStates);
    }

    private char [] getStateFromQueue() {
        return statesQueue.poll().toCharArray();
    }

    private boolean stateHasTransitionForInput(char stateNumChar, String inputSymb){
        //from char to int
        return dfaStates[stateNumChar-48].getTransitions().get(inputSymb)!= null;
    }
    private void getInputFromNFAfile(){
        try {
            inputHandler.getStatesFromFile("nfa");
        }
       catch (Exception e) {
            e.printStackTrace();
        }
        startStateID= inputHandler.getStartStateID();
        inputSymbols = inputHandler.getInputSymbols();
        dfaStates= inputHandler.getDfaStates();
        finalStates = inputHandler.getFinalStates();
    }
    private void initialiseDFAString() {
        dfaString = dfaString + "start " + startStateID + "\n";
    }
    private void initialiseStatesMap() {
        statesMap.put(startStateID + "", 0);
    }
    private void initialiseStatesQueue() {
        statesQueue.add(startStateID + "");
    }
    private boolean statesQueueHasElements(){
        if(statesQueue.isEmpty())
            return false;
        return true;
    }
    private boolean stateIsFinal(State state) {
        return finalStates.contains(state);
    }
}
