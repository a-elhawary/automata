package engine.pda;

class PDATransition {
    public int fromState;
    public char character;
    public String toStates;
    public String oldStack;
    public String newStack;

    public PDATransition(int fromState, char character, String toStates, String oldStack, String newStack) {
        this.fromState = fromState;
        this.character = character;
        this.toStates = toStates;
        this.oldStack = oldStack;
        this.newStack = newStack;
    }
}
