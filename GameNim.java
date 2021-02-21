import java.util.Set;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class GameNim extends Game {

    double winScore = 100;
    double loseScore = -100;

    public GameNim() {
        this.currentState = new StateNim();
    }


    @Override
    public boolean isWinState(State state) {
        StateNim currentState = (StateNim) state;
        return currentState.numCoins <= 1;
    }

    @Override
    public boolean isStuckState(State state) {
        return false;
    }

    @Override
    public Set<State> getSuccessors(State state) {
        StateNim currentState = (StateNim) state;
		Set<State> successors = new HashSet<State>();
        StateNim successor_state;
        for (int i = 1; i <= 3; i++) {
            if (i >= currentState.numCoins) {
                break;
            }
            successor_state = new StateNim(currentState);
            successor_state.numCoins -= i;
            successor_state.togglePlayer();
            successors.add(successor_state);
        }
        return successors;
    }

    @Override
    public double eval(State state) {
        if (state.player == 1) {
            return loseScore;
        } else return winScore;
    }
    

    public static void main(String[] args) throws IOException {
        
        Game game = new GameNim();
        Search search = new Search(game);
        int depth = 12;

        int move;

        StateNim currentState = (StateNim) game.currentState;

        System.out.println("Bag contains 13 coins...");
        System.out.println("Each player can choose to remove 1, 2, or 3 coins at a time in each move...");
        System.out.println("Player left with last coin in bag loses...");
        System.out.println("Game Begins!\n");


        while(true) {

            switch(currentState.player) {
                default:
                case 1:
                move = getMove();
                System.out.println("You choose to remove " + move + " coins from the bag of " + currentState.numCoins + " coins.");
                currentState.numCoins -= move;
                System.out.println(currentState);
                break;

                case 0:
                move = currentState.numCoins - ((StateNim)search.bestSuccessorState(depth)).numCoins;
                System.out.println("Computer chose to remove " + move + " coins from the bag of " + currentState.numCoins + " coins.");
                currentState.numCoins -= move;
                System.out.println(currentState);
                break;
            }
            
            if (game.isWinState(currentState)) {
                if (currentState.player == 1) {
                    System.out.println("\nGame Over: You Win!");
                } else {
                    System.out.println("\nGame Over: Computer Wins!");
                }
                return;
            }
            
            currentState.togglePlayer();
        }
        
    }

    private static int getMove() throws IOException {
        System.out.print("Enter 1, 2 or 3 as your move:\t");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int move = Integer.parseInt(br.readLine());
        return move > 3 ? 3 : (move < 1 ? 1 : move);
    }

}
