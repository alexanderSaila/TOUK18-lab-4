
import java.util.List;

public class Controller {

    private GUI gui;
    private Board board;
    private RuleEngine ruleEngine;
    private InputValidator inputValidator;
    private boolean isGameOver;

    public Controller(){
        gui = new GUI(this);
        ruleEngine = new RuleEngine(this);
        inputValidator = new InputValidator();
        isGameOver = false;
    }

    public void setGameRules(){
        int gridSize = inputValidator.validateGridSize(gui.getGridInput());
        int winLength = inputValidator.validateWinLength(gui.getWinInput());

        if(gridSize == 0){
            gui.errorScreen("Invalid Grid Size");
            return;
        }else if(winLength == 0){
            gui.errorScreen("Invalid Win Requirement.");
            return;
        }

        board = new Board(gridSize*gridSize);

        ruleEngine.setGridSize(gridSize);
        ruleEngine.setLengthNeededToWin(winLength);

        gui.selectPlayers();
    }

    public void setPlayerCount(int playerCount){
        ruleEngine.setPlayerCount(playerCount);
    }

    public int getPlayerCount(){
        return ruleEngine.getPlayerCount();
    }

    public char[] getSpots(){
        return board.getSpots();
    }

    public void requestSymbols(){
        gui.selectSymbol(getPlayerCount());
    }

    public boolean extractSymbols(List<String> inputs){
        inputValidator.clearBusyCharacters();

        int counter = 0;
        char[] symbols = new char[getPlayerCount()];

        for(String input : inputs){
            char tempChar = inputValidator.validateSymbol(input);
            if(tempChar != '.'){
                symbols[counter++] = tempChar;
            }
            else return false;
        }

        ruleEngine.setPlayerSymbols(symbols);
        return true;
    }

    public void startGameScreen(){
        gui.gameScreen(ruleEngine.getGridSize());
    }

    public void makeMove(int spot){
        if(isGameOver){
            return;
        }

        if(!board.isSpotOccupied(spot)){
            char player = ruleEngine.getNextPlayerSymbol();

            board.occupySpot(spot, player);
            gui.occupySpot(spot, player);
            ruleEngine.progressGame();
        }
    }

    public void gameWon(){
        isGameOver = true;
        int winner = ruleEngine.getWinner();
        gui.showWinningSlots(ruleEngine.getWinningIndexes());
        gui.winScreen(winner);
    }

    public void gameLost(){
        isGameOver = true;
        gui.gameOverScreen();
    }

    public void restartGame(){
        gui.dispose();
        new Controller();
    }

}
