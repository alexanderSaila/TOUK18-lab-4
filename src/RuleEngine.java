public class RuleEngine {

    private Controller controller;

    private int lengthNeededToWin;
    private int gridSize;
    private int playerCount;
    private int turn;

    private int[] winningIndexes;
    private  char[] playerSymbols;


    public RuleEngine(Controller controller){
        this.controller = controller;
        playerCount = 2;
        turn = 0;
    }

    public void setPlayerCount(int playerCount) {
        if(playerCount > 1) {
            this.playerCount = playerCount;
            playerSymbols = new char[playerCount];
        }
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public int getGridSize(){
        return gridSize;
    }

    public void setLengthNeededToWin(int lengthNeededToWin){
        this.lengthNeededToWin = lengthNeededToWin;
        this.winningIndexes = new int[lengthNeededToWin];
    }

    public int[] getWinningIndexes() {
        return winningIndexes;
    }

    public void setPlayerSymbols(char[] symbols){
        playerSymbols = symbols;
    }

    public void progressGame(){
        if(checkWinCondition(controller.getSpots())){
            controller.gameWon();
        }
        else if (turn == (gridSize*gridSize)-1) {
            controller.gameLost();
        }
        else {
            turn++;
        }
    }

    public char getNextPlayerSymbol(){
        return playerSymbols[turn%playerCount];
    }

    public int getWinner(){
        return (turn%playerCount)+1;
    }

    private boolean checkWinCondition(char[] spots){

        if(checkHorizontal(spots) || checkVertical(spots)) {
            return true;
        }
        //check diagonal
        return false;
    }

    private boolean checkHorizontal(char[] spots){
        for(int row = 0; row< gridSize * gridSize; row+= gridSize){
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
        for(int row = 0; row< gridSize; row+=1){
            char currentPlayer = '.';
            int counter = 0;

            for(int spot = row; spot< gridSize * gridSize; spot+= gridSize){
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


        return false;
    }

}
