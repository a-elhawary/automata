package engine.dfa;

import engine.dfa.Logic.NFAtoDFA;

import java.io.IOException;

public class main {

        private static NFAtoDFA nfaToDfaConverter = new NFAtoDFA();
        public static void main(String[] args) throws IOException {
            System.out.println("Converting NFA to DFA");
            nfaToDfaConverter.convertToDfa();
    }
}
