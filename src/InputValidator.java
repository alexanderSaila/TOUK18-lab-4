import java.util.ArrayList;
import java.util.List;

public class InputValidator {

    private List<Character> busyCharacters;

    public InputValidator(){
        busyCharacters = new ArrayList<>();
    }

    public int validateGridSize(String input){
        try{
            int parsedInt = Integer.parseInt(input);
            return parsedInt;
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int validateWinLength(String input, int gridSize){
        try {
            int parsedInt = Integer.parseInt(input);
            if(parsedInt > gridSize){
                throw new NumberFormatException("Invalid size for win condition");
            }
            return parsedInt;
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void clearBusyCharacters(){
        busyCharacters = new ArrayList<>();
    }

    public boolean isValidPlayerCount(int playerCount){
        return (playerCount < 2);
    }

    public char validateSymbol(String input){
        input = input.trim();

        if(input.matches("[a-zA-Z]")){
            char tempChar = Character.toUpperCase(input.charAt(0));
            if(!busyCharacters.contains(tempChar)) {
                busyCharacters.add(tempChar);
                return tempChar;
            }
        }
        System.out.println(input + " IS NOT VALID");
        return '.';
    }
}
