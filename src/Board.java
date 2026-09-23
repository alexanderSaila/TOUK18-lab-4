import java.util.Arrays;

public class Board {

    private char[] spots;

    public Board(int fullGridSize){
        spots = new char[fullGridSize];
        Arrays.fill(spots, '.');
    }

    public char[] getSpots() {
        return spots;
    }

    public boolean isSpotOccupied(int spot){
        return !(spots[spot] == '.');
    }

    public void occupySpot(int spot, char player){
        spots[spot] = player;
    }
}
