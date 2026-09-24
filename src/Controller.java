
import java.util.List;

public class Controller {

    private GameUI gui;
    private Board board;
    private RuleEngine ruleEngine;
    private InputValidator inputValidator;
    private boolean isGameOver;

    public Controller(){
        inputValidator = new InputValidator();
        isGameOver = false;
    }

    public void setGui(GameUI gui){
        this.gui = gui;
        gui.startScreen();
    }

    public void setRuleEngine(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    public void setGameRules(String gridSizeInput, String winLengthInput, int playerCount){

        int gridSize = inputValidator.validateGridSize(gridSizeInput);
        if(gridSize == 0) {
            gui.errorScreen("Invalid Grid Size");
            return;
        }

        int winLength = inputValidator.validateWinLength(winLengthInput, gridSize);
        if(winLength == 0) {
            gui.errorScreen("Invalid Win Requirement.");
            return;
        }

        if (inputValidator.isValidPlayerCount(playerCount)) {
            gui.errorScreen("Invalid Player Count: " + playerCount);
            return;
        }

        board = new Board(gridSize);

        ruleEngine.setup(board, winLength, playerCount);

        gui.selectSymbolScreen();
    }

    public boolean extractSymbols(List<String> inputs){
        inputValidator.clearBusyCharacters();

        int counter = 0;
        char[] symbols = new char[inputs.size()];

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
        gui.gameScreen(board.getGridSize());
    }

    public void makeMove(int spot){
        if(isGameOver){
            return;
        }
        gui.updatePlayerTurn(ruleEngine.getCurrentPlayer());

        if(!board.isSpotOccupied(spot)){
            char player = ruleEngine.getNextPlayerSymbol();

            board.occupySpot(spot, player);
            gui.occupySpot(spot, player);
            switch (ruleEngine.progressGame()){
                case GAME_WON -> {
                    gui.showWinningSlots(ruleEngine.getWinningIndexes());
                    endGame("Winner player " + ruleEngine.getCurrentPlayer());
                }
                case GAME_OVER -> endGame("Game Over");
                case IN_PROGRESS -> { gui.updatePlayerTurn(ruleEngine.getCurrentPlayer());}
            }
        }
    }

    private void endGame(String message){
        isGameOver = true;
        gui.endGameScreen(message);
    }

    public void restartGame(){
        gui.dispose();
        Main.startGame();
    }

}
