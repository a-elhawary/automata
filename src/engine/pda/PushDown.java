package engine.pda;

import java.util.ArrayList;

public class PushDown {
    public static String preProcessing(String grammar){
        // remove whitespace
        String newGrammar = "";
        for(int i = 0; i < grammar.length(); i++){
            if(grammar.charAt(i) != ' '){
                newGrammar += grammar.charAt(i);
            }
        }
        if(newGrammar.charAt(newGrammar.length() - 1) != '\n'){
           newGrammar += '\n';
        }
        grammar = newGrammar;
        // turn A -> B | C to
        // A -> B
        // A -> C
        String[] productionRules = grammar.split("\n");
        ArrayList<String> newProdRules = new ArrayList<>(productionRules.length);
        for(int i = 0; i < productionRules.length; i++){
            String LHS = productionRules[i].split("-")[0];
            String RHS = productionRules[i].split(">")[1];
            String[] miniRules = RHS.split("\\|");
            for(int j = 0; j < miniRules.length; j++){
                String rule = LHS + "->" + miniRules[j];
                newProdRules.add(rule);
            }
        }
        grammar = "";
        for(int i = 0; i < newProdRules.size(); i++){
            grammar += newProdRules.get(i) + "\n";
        }
        return grammar;
    }

    public static PDAutomata generatePDA(String grammar, String startNonTerminal){
        grammar = preProcessing(grammar);
        PDAutomata pda = new PDAutomata();
        pda.addState("q0"); // initial state
        pda.addState("q1"); // loop state
        pda.addState("q2"); // final state
        pda.setStartState("q0");
        pda.addFinalState("q2");

        // add  transition from initial state to loop state that adds starting char
        pda.addTransition("q0", "q1", PDAutomata.EPSILON, "","$"+startNonTerminal);

        // add transition from loop state to final state
        pda.addTransition("q1", "q2", '$', "$",""+PDAutomata.EPSILON);

        String[] productionRules = grammar.split("\n");
        for(int i = 0; i < productionRules.length; i++){
            String LHS = productionRules[i].split("-")[0];
            String RHS = productionRules[i].split(">")[1];
            if(RHS.charAt(0) >= 'A'){
                pda.addTransition("q1", "q1", PDAutomata.EPSILON, LHS,RHS);
            }else{
                pda.addTransition("q1", "q1", RHS.charAt(0),LHS,RHS.substring(1));
            }
        }
        return pda;
    }

    public static void main(String args[]){
        String grammar="A -> 0AB | C\n A -> e \n B -> 1 \n C -> 3";
        PDAutomata pda = PushDown.generatePDA(grammar, "A");
        System.out.println(pda.toString());
    }
}
