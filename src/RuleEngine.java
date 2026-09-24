public class RuleEngine {

    private Board board;

    private int lengthNeededToWin;
    private int playerCount;
    private int turn;

    private int[] winningIndexes;
    private  char[] playerSymbols;


    public RuleEngine(){
        playerCount = 2;
        turn = 0;
    }

    public void setup(Board board, int winLength, int playerCount){
        this.board = board;
        this.lengthNeededToWin = winLength;
        this.winningIndexes = new int[lengthNeededToWin];
        this.playerCount = playerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int[] getWinningIndexes() {
        return winningIndexes;
    }

    public void setPlayerSymbols(char[] symbols){
        playerSymbols = symbols;
    }

    public GameState progressGame(){
        if(checkWinCondition(board.getSpots())){
            return GameState.GAME_WON;
        }
        else if (turn == (board.getFullGridSize())-1) {
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

    private boolean checkWinCondition(char[] spots){

        if(checkHorizontal(spots) || checkVertical(spots) || checkDiagonal(spots)) {
            return true;
        }
        return false;
    }

    private boolean checkHorizontal(char[] spots){
        int gridSize = board.getGridSize();
        for(int row = 0; row< board.getFullGridSize(); row+= gridSize){
            char currentPlayer = '.';
            int counter = 0;

            for(int spot = row; spot<row+ gridSize; spot++){
                char currentSpot = spots[spot];

                if(currentSpot != '.'){

                    if(currentPlayer != currentSpot) {
                        currentPlayer = currentSpot;
                        counter = 1;

                    }
                    else {
                        counter++;
                    }
                    winningIndexes[counter-1] = spot;
                    if(counter == lengthNeededToWin){
                        return true;
                    }
                }
                else {
                    currentPlayer = '.';
                    counter = 0;
                }
            }
        }
        return false;
    }

    private boolean checkVertical(char[] spots){
        int gridSize = board.getGridSize();
        for(int column = 0; column< gridSize; column+=1){
            char currentPlayer = '.';
            int counter = 0;

            for(int spot = column; spot< board.getFullGridSize(); spot+= gridSize){
                char currentSpot = spots[spot];

                if(currentSpot != '.'){

                    if(currentPlayer != currentSpot) {
                        currentPlayer = currentSpot;
                        counter = 1;
                    }
                    else {
                        counter++;
                    }
                    winningIndexes[counter-1] = spot;
                    if(counter == lengthNeededToWin){
                        return true;
                    }
                }
                else {
                    currentPlayer = '.';
                    counter = 0;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal(char[] spots){
        int gridSize = board.getGridSize();
        int fullGridSize = board.getFullGridSize();

        for(int i=0; i<gridSize; i++){ //check down right while moving right
            String result = getDiagonalPositions(i, fullGridSize, gridSize+1, gridSize-i);
            if(resolveDiagonalResult(result)){
                return true;
            }

            result = getDiagonalPositions(i*gridSize, fullGridSize, gridSize + 1, gridSize - i);
            if(resolveDiagonalResult(result)){
                return true;
            }

            result = getDiagonalPositions(fullGridSize-gridSize-gridSize*i,fullGridSize,-(gridSize-1), gridSize-i);
            if(resolveDiagonalResult(result)){
                return true;
            }

            result = getDiagonalPositions(fullGridSize-gridSize-gridSize*i,fullGridSize,-(gridSize-1), gridSize-i);
            if(resolveDiagonalResult(result)){
                return true;
            }
        }

        return false;
    }

    private String getDiagonalPositions(int startPos, int fullGridSize, int jumpLength, int iteration) {
        if (iteration <= 0 || startPos >= fullGridSize) {
            return "";
        }

        return startPos + "," + getDiagonalPositions(startPos + jumpLength, fullGridSize, jumpLength, iteration - 1);
    }

    private boolean resolveDiagonalResult(String result){
        String[] splits = result.split(",");

        if(splits.length < lengthNeededToWin){
            return false;
        }

        System.out.println("Resolving 'array' " + result);

        char currentPlayer = '.';
        int counter = 0;

        for(int i=0; i<splits.length; i++){
            int currentIndex = Integer.parseInt(splits[i]);
            char currentSpot = board.getSpots()[currentIndex];
            if(currentSpot != '.'){

                if(currentPlayer != currentSpot) {
                    currentPlayer = currentSpot;
                    counter = 1;
                }
                else {
                    counter++;
                }
                winningIndexes[counter-1] = currentIndex;
                if(counter == lengthNeededToWin){
                    return true;
                }
            }
            else {
                currentPlayer = '.';
                counter = 0;
            }
        }
        return false;
    }

}
