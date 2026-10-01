
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<GameUI> guiList;
    private Board board;
    private RuleEngine ruleEngine;
    private InputValidator inputValidator;
    private boolean isGameOver;

    public Controller(){
        inputValidator = new InputValidator();
        isGameOver = false;
        guiList = new ArrayList<>();
    }

    public void addGui(GameUI gui){
        guiList.add(gui);
    }

    public void setRuleEngine(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    public void setGameRules(String gridSizeInput, String winLengthInput, int playerCount){

        GameUI gui = guiList.get(0);

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

        if (!inputValidator.isValidPlayerCount(playerCount)) {
            gui.errorScreen("Invalid Player Count: " + playerCount);
            return;
        }

        board = new Board(gridSize);

        ruleEngine.setup(board, winLength, playerCount);

        for(int i=1; i<playerCount; i++){
            GameUI tempGui = new GUI(this, i+1);
            addGui(tempGui);
        }

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
        for(GameUI gui : guiList) {
            gui.gameScreen(board.getGridSize());
        }
    }

    public void makeMove(int spot, int playerID){
        if(isGameOver || !(ruleEngine.isCorrectPlayerTurn(playerID))){
            return;
        }
        for(GameUI gui : guiList) {
            gui.updatePlayerTurn(ruleEngine.getCurrentPlayer());
        }

        if(!board.isSpotOccupied(spot)){
            char player = ruleEngine.getNextPlayerSymbol();

            board.occupySpot(spot, player);
            for(GameUI gui : guiList) {
                gui.occupySpot(spot, player);
            }
            switch (ruleEngine.progressGame()){
                case GAME_WON -> {
                    for(GameUI gui : guiList) {
                        gui.showWinningSlots(ruleEngine.getWinningIndexes());
                    }
                    endGame("Winner player " + ruleEngine.getCurrentPlayer());
                }
                case GAME_OVER -> endGame("Game Over");
                case IN_PROGRESS -> {
                    for(GameUI gui : guiList) {
                        gui.updatePlayerTurn(ruleEngine.getCurrentPlayer());
                    }
                }
            }
        }
    }

    private void endGame(String message){
        isGameOver = true;
        guiList.get(0).endGameScreen(message);
    }

    public void restartGame(){
        for(GameUI gui : guiList) {
            gui.dispose();
        }
        Main.startGame();
    }

}
