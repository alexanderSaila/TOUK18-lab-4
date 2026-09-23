import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI {

    private Controller controller;
    private JButton[] buttons;
    private JFrame frame;
    private JTextField gridInputField;
    private JTextField winInputField;

    public GUI(Controller controller){
        this.controller = controller;

        frame = new JFrame("TerribleTicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startScreen();
    }

    public String getGridInput(){
        return gridInputField.getText();
    }

    public String getWinInput(){
        return winInputField.getText();
    }

    public void startScreen(){
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(3,1));

        JPanel gridFieldArea = new JPanel(new GridLayout(1,2));
        JPanel winFieldArea = new JPanel(new GridLayout(1,2));
        JPanel submitFieldArea = new JPanel(new FlowLayout());

        JLabel gridText = new JLabel("Grid:", SwingConstants.CENTER);
        JLabel winText = new JLabel("Win Condition:", SwingConstants.CENTER);

        gridInputField = new JTextField("");
        winInputField = new JTextField("");

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            controller.setGameRules();
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

        submitFieldArea.add(submitButton);
        submitFieldArea.add(exitButton);

        frame.add(gridFieldArea);
        frame.add(winFieldArea);
        frame.add(submitFieldArea);

        frame.repaint();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void gameScreen(int rowSize){
        frame.getContentPane().removeAll();

        frame.setLayout(new GridLayout(rowSize,rowSize));
        frame.setSize(300,300);

        int arraySize = rowSize*rowSize;
        buttons = new JButton[arraySize];

        for(int i=0;i<arraySize;i++){

            JButton tempButton = new JButton("");
            buttons[i] = tempButton;
            int spot = i;

            tempButton.addActionListener(e -> {
                controller.makeMove(spot);
            });

            frame.add(tempButton);
        }

        frame.repaint();
        frame.revalidate();
        frame.setVisible(true);
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

    public void selectPlayers(){
        frame.getContentPane().removeAll();

        frame.setLayout(new GridLayout(2,1));

        JPanel topPanel = new JPanel(new FlowLayout());
        JPanel bottomPanel = new JPanel(new FlowLayout());

        JButton decButton = new JButton("-");
        JLabel playerCountLabel = new JLabel("" + controller.getPlayerCount());
        JButton incButton = new JButton("+");
        JButton submitButton = new JButton("Submit");

        decButton.addActionListener((e) -> {
            controller.setPlayerCount(controller.getPlayerCount()-1);
            playerCountLabel.setText("" + controller.getPlayerCount());
            frame.repaint();
        });

        incButton.addActionListener(e -> {
            controller.setPlayerCount(controller.getPlayerCount()+1);
            playerCountLabel.setText("" + controller.getPlayerCount());
            frame.repaint();
        });

        submitButton.addActionListener(e -> {
            controller.requestSymbols();
        });

        topPanel.add(decButton);
        topPanel.add(playerCountLabel);
        topPanel.add(incButton);
        bottomPanel.add(submitButton);

        frame.add(topPanel);
        frame.add(bottomPanel);

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    public void selectSymbol(int playerCount){
        frame.getContentPane().removeAll();

        frame.setLayout(new GridLayout(playerCount+1,1));

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

    public void winScreen(int player){
        JDialog window = new JDialog();
        window.setLayout(new GridLayout(2,1));

        JPanel topPanel = new JPanel(new FlowLayout());
        JPanel bottomPanel = new JPanel(new FlowLayout());

        JLabel label = new JLabel("Winner Player " + player);
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

    public void gameOverScreen(){
        JDialog window = new JDialog();
        window.setLayout(new GridLayout(2,1));

        JPanel topPanel = new JPanel(new FlowLayout());
        JPanel bottomPanel = new JPanel(new FlowLayout());

        JLabel label = new JLabel("Game Over!");
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
