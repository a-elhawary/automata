package engine.dfa.InputHandler;
import engine.dfa.Model.FinalStates;
import engine.dfa.Model.State;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Input {
    private int startStateID;
    private Set<String> inputSymbols;
    private State dfaStates[];
    private FinalStates FinalStates;
    private int NumOfStates = 0;

    public void getStatesFromFile(String file) throws Exception{
        BufferedReader nfaReader= getBufferReader(file);
        NumOfStates = getNumOfStates(nfaReader);
        StateNames0toN();
        String NFALine;
        inputSymbols = new HashSet<>();

        NFALine = nfaReader.readLine();
        startStateID= getstartStateID(NFALine);
        NFALine = nfaReader.readLine();
        while(StatesNotFinal(NFALine)){
            StringTokenizer st = new StringTokenizer(NFALine);
            // FROM(1) AT a TO (2)
            int from = Integer.parseInt(st.nextToken());
            String at = st.nextToken();
            int to = Integer.parseInt(st.nextToken());

            dfaStates[from].enterState(at,dfaStates[to]);
            inputSymbols.add(at);
            NFALine = nfaReader.readLine();

        }
        FinalStates = new FinalStates(0);
        StringTokenizer st = new StringTokenizer(NFALine);
        st.nextToken();
        while (st.hasMoreTokens()){
            FinalStates.add(dfaStates[Integer.parseInt(st.nextToken())]);
        }
    }
    public int getStartStateID() {
        return this.startStateID;
    }
    public State[] getDfaStates(){
        return this.dfaStates;
    }
    public Set<String> getInputSymbols() {
        return this.inputSymbols;
    }
    public FinalStates getFinalStates() {
        return this.FinalStates;
    }

    private int getstartStateID() {
        return this.startStateID;
    }
    private int getstartStateID(String nfaLine){
        return Integer.parseInt(nfaLine.substring(6));
    }

    private BufferedReader getBufferReader(String file) {
        BufferedReader nfaReader = null;
        try {
            nfaReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            return nfaReader;
        }

    }
    private int getNumOfStates(BufferedReader nfafileReader) {
        try {
            return Integer.parseInt(nfafileReader.readLine());
        }
        catch (NumberFormatException| IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    private void StateNames0toN()
    {
        dfaStates = new State[NumOfStates];
        for (int i=0;i<NumOfStates;i++)
            dfaStates[i] =new State(i);
    }

    private boolean StatesNotFinal(String nfaLine){
        if(nfaLine.startsWith("final"))
            return false;
        return true;
    }
}
