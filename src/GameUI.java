public interface GameUI {

    void startScreen();

    void gameScreen(int rowSize);

    void errorScreen(String message);

    void endGameScreen(String messange);

    void occupySpot(int spot, char symbol);

    void updatePlayerTurn(int playerNumber);

    void selectSymbolScreen();

    void showWinningSlots(int[] indexes);

    void dispose();
}
