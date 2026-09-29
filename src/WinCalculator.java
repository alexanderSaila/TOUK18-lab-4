public class WinCalculator {
    private Board board;
    private int[] winningIndexes;
    private int lengthNeededToWin;

    public WinCalculator(Board board, int winLength){
        this.board = board;
        this.lengthNeededToWin = winLength;
        this.winningIndexes = new int[lengthNeededToWin];
    }

    public int[] getWinningIndexes() {
        return winningIndexes;
    }

    public boolean checkWinCondition(){
        return checkHorizontal() || checkVertical() || checkDiagonal();
    }

    private boolean checkHorizontal(){
        int gridSize = board.getGridSize();
        for(int row = 0; row< board.getFullGridSize(); row+= gridSize){
            char currentPlayer = '.';
            int counter = 0;

            for(int spot = row; spot<row+ gridSize; spot++){
                char currentSpot = board.getCharAtSpot(spot);

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

    private boolean checkVertical(){
        int gridSize = board.getGridSize();
        for(int column = 0; column< gridSize; column+=1){
            char currentPlayer = '.';
            int counter = 0;

            for(int spot = column; spot< board.getFullGridSize(); spot+= gridSize){
                char currentSpot = board.getCharAtSpot(spot);

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

    private boolean checkDiagonal(){
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

            result = getDiagonalPositions(fullGridSize-gridSize+i,fullGridSize,-(gridSize-1), gridSize-i);
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

        char currentPlayer = '.';
        int counter = 0;

        for(int i=0; i<splits.length; i++){
            int currentIndex = Integer.parseInt(splits[i]);
            char currentSpot = board.getCharAtSpot(currentIndex);
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
