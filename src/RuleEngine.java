public class RuleEngine {

    private WinCalculator winCalculator;

    private int fullGridSize;
    private int playerCount;
    private int turn;

    private  char[] playerSymbols;


    public RuleEngine(){
        playerCount = 2;
        turn = 0;
    }

    public void setup(Board board, int winLength, int playerCount){
        this.winCalculator = new WinCalculator(board, winLength);
        this.fullGridSize = board.getFullGridSize();
        this.playerCount = playerCount;
    }

    public int[] getWinningIndexes() {
        return winCalculator.getWinningIndexes();
    }

    public void setPlayerSymbols(char[] symbols){
        playerSymbols = symbols;
    }

    public GameState progressGame(){
        if(winCalculator.checkWinCondition()){
            return GameState.GAME_WON;
        }
        else if (turn == (fullGridSize-1)) {
            return GameState.GAME_OVER;
        }
        else {
            turn++;
            return GameState.IN_PROGRESS;
        }
    }

    public char getNextPlayerSymbol(){
        return playerSymbols[turn%playerCount];
    }

    public int getCurrentPlayer(){
        return (turn%playerCount)+1;
    }

}
