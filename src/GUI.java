import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI implements GameUI {

    private Controller controller;
    private JButton[] buttons;
    private JFrame frame;
    private JLabel playerInTurn;
    private int playerCount;

    public GUI(Controller controller){
        this.controller = controller;
        playerCount = 2;

        frame = new JFrame("TerribleTicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startScreen(){
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel gridFieldArea = new JPanel(new GridLayout(1,2));
        JPanel winFieldArea = new JPanel(new GridLayout(1,2));
        JPanel submitFieldArea = new JPanel(new FlowLayout());

        JLabel gridText = new JLabel("Grid:", SwingConstants.CENTER);
        JLabel winText = new JLabel("Win Condition:", SwingConstants.CENTER);

        JTextField gridInputField = new JTextField("");
        JTextField winInputField = new JTextField("");

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            controller.setGameRules(gridInputField.getText(), winInputField.getText(), playerCount);
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(1);
        });

        frame.setSize(300, 200);

        gridFieldArea.add(gridText);
        gridFieldArea.add(gridInputField);

        winFieldArea.add(winText);
        winFieldArea.add(winInputField);

        JPanel inputFields = new JPanel(new GridLayout(2,1));
        inputFields.add(gridFieldArea);
        inputFields.add(winFieldArea);

        submitFieldArea.add(submitButton);
        submitFieldArea.add(exitButton);

        frame.add(inputFields, BorderLayout.CENTER);
        frame.add(selectPlayersField(), BorderLayout.NORTH);
        frame.add(submitFieldArea, BorderLayout.SOUTH);

        frame.revalidate();
        frame.repaint();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void gameScreen(int rowSize){
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel gamePanel = gridField(rowSize);
        frame.add(gamePanel, BorderLayout.CENTER);

        JLabel currentTurn = new JLabel("Current Turn: Player ");
        playerInTurn = new JLabel("1");

        JPanel turnOrderArea = new JPanel(new FlowLayout());

        turnOrderArea.add(currentTurn);
        turnOrderArea.add(playerInTurn);
        frame.add(turnOrderArea, BorderLayout.SOUTH);

        frame.setSize(300,300);
        frame.repaint();
        frame.revalidate();
        frame.setVisible(true);
    }

    private JPanel selectPlayersField(){
        JPanel selectionField = new JPanel(new BorderLayout());

        JLabel playerAmountLabel = new JLabel("Amount of Players", SwingConstants.CENTER);
        JButton decButton = new JButton("-");
        JLabel playerCountLabel = new JLabel("" + playerCount);
        JButton incButton = new JButton("+");

        decButton.addActionListener((e) -> {
            playerCount--;
            playerCountLabel.setText("" + playerCount);
            frame.repaint();
        });

        incButton.addActionListener(e -> {
            playerCount++;
            playerCountLabel.setText("" + playerCount);
            frame.repaint();
        });

        JPanel inputField = new JPanel(new FlowLayout());
        inputField.add(decButton);
        inputField.add(playerCountLabel);
        inputField.add(incButton);
        selectionField.add(inputField, BorderLayout.SOUTH);
        selectionField.add(playerAmountLabel, BorderLayout.NORTH);

        return selectionField;
    }

    private JPanel gridField(int rowSize){
        JPanel gridField = new JPanel(new GridLayout(rowSize,rowSize));
        int arraySize = rowSize*rowSize;
        buttons = new JButton[arraySize];

        for(int i=0;i<arraySize;i++){

            JButton tempButton = new JButton("");
            buttons[i] = tempButton;
            int spot = i;

            tempButton.addActionListener(e -> {
                controller.makeMove(spot);
            });

            gridField.add(tempButton);
        }

        return gridField;
    }

    public void updatePlayerTurn(int playerNumber) {
        playerInTurn.setText("" + playerNumber);
    }

    public void errorScreen(String message){
        JDialog window = new JDialog();
        window.setLayout(new GridLayout(2,1));

        JPanel submitArea = new JPanel(new FlowLayout());
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        JButton submitButton = new JButton("OK");

        submitButton.addActionListener(e -> {
            window.dispose();
        });

        submitArea.add(submitButton);
        window.add(label);
        window.add(submitArea);

        window.setSize(150,150);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void selectSymbolScreen(){
        frame.getContentPane().removeAll();

        frame.setLayout(new GridLayout(playerCount+2,1));

        JLabel title = new JLabel("Pick Players Symbols", SwingConstants.CENTER);
        frame.add(title);

        List<JTextField> textFields = new ArrayList<>();

        for(int i=1; i<=playerCount; i++){
            JPanel area = new JPanel(new GridLayout(1,2));
            JLabel playerLabel = new JLabel("Player " + i + ":");
            JTextField inputField = new JTextField("");

            textFields.add(inputField);

            area.add(playerLabel);
            area.add(inputField);
            frame.add(area);
        }

        JButton submitButton = new JButton("Start Game");
        submitButton.addActionListener(e -> {
            List<String> inputStrings = new ArrayList<>();
            for(JTextField field : textFields){
                inputStrings.add(field.getText());
            }
            if(!controller.extractSymbols(inputStrings)){
                errorScreen("Invalid Characters.");
            }
            else {
                controller.startGameScreen();
            }
        });
        frame.add(submitButton);

        frame.setSize(200, playerCount*100);

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    public void endGameScreen(String message){
        JDialog window = new JDialog();
        window.setLayout(new GridLayout(2,1));

        JPanel topPanel = new JPanel(new FlowLayout());
        JPanel bottomPanel = new JPanel(new FlowLayout());

        JLabel label = new JLabel(message);
        JButton button = new JButton("Continue");

        button.addActionListener(e -> {
            window.dispose();
            controller.restartGame();
        });

        topPanel.add(label);
        bottomPanel.add(button);

        window.add(topPanel);
        window.add(bottomPanel);

        window.setSize(200,200);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void occupySpot(int spot, char symbol){
        buttons[spot].setText(""+symbol);
    }

    public void showWinningSlots(int[] indexes){
        for(int index : indexes){
            buttons[index].setBackground(Color.yellow);
        }
    }

    public void dispose(){
        frame.dispose();
    }

}
