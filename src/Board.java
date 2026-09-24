import java.util.Arrays;

public class Board {

    private char[] spots;
    private int gridSize;
    private int fullGridSize;

    public Board(int gridSize){
        this.gridSize = gridSize;
        this.fullGridSize = gridSize*gridSize;
        spots = new char[fullGridSize];
        Arrays.fill(spots, '.');
    }

    public char[] getSpots() {
        return spots;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getFullGridSize() {
        return fullGridSize;
    }

    public boolean isSpotOccupied(int spot){
        return !(spots[spot] == '.');
    }

    public void occupySpot(int spot, char player){
        spots[spot] = player;
    }
}
