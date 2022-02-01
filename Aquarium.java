/**
 * Aquarium represents a single problem in the game Aquarium.
 *
 * @author Lyndon While & Carmen Leong(22789943) & Miguel Lima(22967427)
 * @version 2020
 */
import java.util.ArrayList;
public class Aquarium
{
    private int   size;         // the board is size x size
    private int[] columnTotals; // the totals at the top of the columns, left to right
    private int[] rowTotals;    // the totals at the left of the rows, top to bottom 
    
    // the board divided into aquariums, numbered from 1,2,3,...
    // spaces with the same number are part of the same aquarium
    private int[][] aquariums;
    // the board divided into spaces, each empty, water, or air
    private Space[][] spaces;

    /**
     * Constructor for objects of class Aquarium. 
     * Creates, initialises, and populates all of the fields.
     */
    public Aquarium(String filename)
    {
        ArrayList<String> filelines = (new FileIO(filename)).getLines();
        filelines.remove(2);
        size = filelines.size()-2;
        int[][] intsarray = new int[size+2][size+2];
        aquariums = new int[size][size];
        spaces = new Space[size][size];
        for (int i = 0; i < size+2 ; i++){
            intsarray[i] = parseLine(filelines.get(i));
        }
        columnTotals = intsarray[0];
        rowTotals = intsarray[1];
        for (int i = 0 ; i < size ; i++){
            aquariums[i] = java.util.Arrays.copyOf(intsarray[i+2],size);
        }
        clear();
    }
    
    /**
     * Uses the provided example file on the LMS page.
     */
    public Aquarium()
    {
        this("Examples/a6_1.txt");
    }

    /**
     * Returns an array containing the ints in s, 
     * each of which is separated by one space. 
     * e.g. if s = "1 299 34 5", it will return {1,299,34,5} 
     */
    public static int[] parseLine(String s)
    {
        String[] stringarray = s.split(" ");
        int[] numarray = new int[stringarray.length];
        int i = 0;
        for (String string : stringarray){
            numarray[i++] = Integer.parseInt(string);
        }
        return numarray;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
       return size;
    }
    
    /**
     * Returns the column totals.
     */
    public int[] getColumnTotals()
    {
        return columnTotals;
    }
    
    /**
     * Returns the row totals.
     */
    public int[] getRowTotals()
    {
        return rowTotals;
    }
    
    /**
     * Returns the board in aquariums.
     */
    public int[][] getAquariums()
    {
        return aquariums;
    }
    
    /**
     * Returns the board in spaces.
     */
    public Space[][] getSpaces()
    {
        return spaces;
    }
    
    /**
     * Performs a left click on Square r,c if the indices are legal, o/w does nothing. 
     * A water space becomes empty; other spaces become water. 
     */
    public void leftClick(int r, int c)
    {
        if (0 <= r && r < size && 0 <= c && c < size){
            if(spaces[r][c]==Space.WATER) spaces[r][c] = Space.EMPTY;
            else spaces[r][c] =  Space.WATER;
        }    
    }
    
    /**
     * Performs a right click on Square r,c if the indices are legal, o/w does nothing. 
     * An air space becomes empty; other spaces become air. 
     */
    public void rightClick(int r, int c)
    {
        if (0 <= r && r < size && 0 <= c && c < size){
            if(spaces[r][c]==Space.AIR) spaces[r][c] = Space.EMPTY;
            else spaces[r][c] = Space.AIR;
        }
    }
    
    /**
     * Empties all of the spaces.
     */
    public void clear()
    {
        for (int i = 0; i < size ; i++)
            for (int j = 0; j < size; j++){
                spaces[i][j] = Space.EMPTY;
            }   
    }
}
