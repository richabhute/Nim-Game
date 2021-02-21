public class StateNim extends State {

    public int numCoins;

    public StateNim() {
        this.numCoins = 13;
    }

    public StateNim(StateNim state) {
        this.player = state.player;
        this.numCoins = state.numCoins;
    }

    public void togglePlayer() {
        this.player = (this.player + 1) % 2;
    }

    public String toString() {
        return "Number of coins left:\t" + this.numCoins + "\n";
    }
}