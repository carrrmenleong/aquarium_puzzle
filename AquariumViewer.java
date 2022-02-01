/**
 * AquariumViewer represents an interface for playing a game of Aquarium.
 *
 * @author Lyndon While & Carmen Leong(22789943) & Miguel Lima(22967427)
 * @version 2020
 */
import java.awt.*;
import java.awt.event.*; 
import javax.swing.SwingUtilities;

public class AquariumViewer implements MouseListener
{
    private final int BOXSIZE = 40;          // the size of each square
    private final int OFFSET  = BOXSIZE * 2; // the gap around the board
    private       int WINDOWSIZE;            // set this in the constructor 
    
    private Aquarium puzzle; // the internal representation of the puzzle
    private int        size; // the puzzle is size x size
    private SimpleCanvas sc; // the display window
    
    private final Color button = Color.lightGray;
    private final Color writing = Color.black;
    private final Color aqua = Color.cyan;
    private final Color bg = Color.white;
    private final Color grid = Color.black;
    private final Color aquaborder = Color.red;

    /**
     * Main constructor for objects of class AquariumViewer.
     * Sets all fields, and displays the initial puzzle.
     */
    public AquariumViewer(Aquarium puzzle)
    {
        this.puzzle = puzzle;
        size = puzzle.getSize();
        WINDOWSIZE = BOXSIZE * size + 2 * OFFSET;
        sc = new SimpleCanvas("Aquarium Puzzle",WINDOWSIZE,WINDOWSIZE,Color.white);
        sc.addMouseListener(this);
        sc.setFont(new Font("Times", Font.BOLD, 20));
        displayPuzzle();
    }
    
    /**
     * Selects from among the provided files in folder Examples. 
     * xyz selects axy_z.txt. 
     */
    public AquariumViewer(int n)
    {
        this(new Aquarium("Examples/a" + n / 10 + "_" + n % 10 + ".txt"));
    }
    
    /**
     * Uses the provided example file on the LMS page.
     */
    public AquariumViewer()
    {
        this(61);
    }
    
    /**
     * Returns the current state of the puzzle.
     */
    public Aquarium getPuzzle()
    {
        return puzzle;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Returns the current state of the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        return sc;
    }
    
    /**
     * Displays the initial puzzle; see the LMS page for the format.
     */
    private void displayPuzzle()
    {
        displayGrid();
        displayNumbers();
        displayAquariums();
        displayButtons();
    }
    
    /**
     * Displays the grid in the middle of the window.
     */
    public void displayGrid()
    {
        for (int i = 0; i <= size; i++){
            sc.drawLine(i * BOXSIZE + OFFSET,OFFSET,i * BOXSIZE + OFFSET, size * BOXSIZE+ OFFSET,grid);
            sc.drawLine(OFFSET, i * BOXSIZE + OFFSET, size * BOXSIZE + OFFSET, i * BOXSIZE + OFFSET, grid);
        }
    }
    
    /**
     * Displays the numbers around the grid.
     */
    public void displayNumbers()
    {
        for (int i = 0; i < size; i++){
            sc.drawString(puzzle.getColumnTotals()[i],BOXSIZE * i + OFFSET + 16, BOXSIZE + 20,writing);
            sc.drawString(puzzle.getRowTotals()[i], BOXSIZE + 15, BOXSIZE * i + OFFSET + 25, writing);
        }
    }
    
    /**
     * Displays the aquariums.
     */
    public void displayAquariums()
    {
        //Draw the outer border
        sc.drawRectangle(OFFSET-2,OFFSET-2,OFFSET + 2,
                         OFFSET + size*BOXSIZE-2,aquaborder);  
        sc.drawRectangle(OFFSET-2,OFFSET-2,OFFSET + size*BOXSIZE-2,
                         OFFSET + 2,aquaborder); 
        sc.drawRectangle(OFFSET + size*BOXSIZE-2, OFFSET-2, 
                         OFFSET + size*BOXSIZE + 2,
                         OFFSET + size*BOXSIZE+2,aquaborder);
        sc.drawRectangle(OFFSET-2, OFFSET + size*BOXSIZE-2, 
                         OFFSET + size*BOXSIZE-2,
                         OFFSET + size*BOXSIZE+2,aquaborder);
        //Draw inner border
        for (int j = 0; j < size; j++)
            for(int i = 1; i < size; i++){
                if (puzzle.getAquariums()[j][i] != puzzle.getAquariums()[j][i-1]){
                    sc.drawRectangle(OFFSET + BOXSIZE*i-2, OFFSET + BOXSIZE*j-2, 
                                     OFFSET + BOXSIZE*i + 2, 
                                     OFFSET + BOXSIZE*(j+1)-2,aquaborder);    
                }
                if (puzzle.getAquariums()[i][j] != puzzle.getAquariums()[i-1][j]){
                    sc.drawRectangle(OFFSET + BOXSIZE*j-2, OFFSET + BOXSIZE*i-2, 
                                     OFFSET + BOXSIZE*(j+1) + 2,
                                     OFFSET + BOXSIZE*i + 2,aquaborder);
                }
            }
    }
    
    /**
     * Displays the buttons below the grid.
     */
    public void displayButtons()
    {
       sc.setFont(new Font("Times", Font.BOLD, 15));
       sc.drawRectangle(OFFSET,OFFSET + BOXSIZE*size + 8, 
                        OFFSET + 90, OFFSET + BOXSIZE*(size+1),button);
       sc.drawRectangle(OFFSET + BOXSIZE*(size-2) + 11, OFFSET + BOXSIZE*size + 8, 
                        OFFSET + BOXSIZE*size, OFFSET + BOXSIZE*(size+1),button);
       sc.drawString("SOLVED?", OFFSET + 10, OFFSET + BOXSIZE*(size+1)-10, writing);
       sc.drawString("CLEAR", OFFSET + BOXSIZE*(size-2) + 20, 
                      OFFSET + BOXSIZE*(size+1)-10, writing);
    }        
    
    /**
     * Updates the display of Square r,c. 
     * Sets the display of this square to whatever is in the squares array. 
     */
    public void updateSquare(int r, int c)
    {
        if (puzzle.getSpaces()[r][c] == Space.EMPTY)
            sc.drawRectangle(OFFSET + BOXSIZE*c + 2,OFFSET + BOXSIZE*r + 2,
                             OFFSET + BOXSIZE*(c+1) - 2, 
                             OFFSET + BOXSIZE*(r+1) - 2,bg);
        else
        if (puzzle.getSpaces()[r][c] == Space.WATER)
            sc.drawRectangle(OFFSET + BOXSIZE*c + 2,OFFSET + BOXSIZE*r + 2,
                             OFFSET + BOXSIZE*(c+1) - 2, 
                             OFFSET + BOXSIZE*(r+1) - 2,aqua);
        else{
            sc.drawRectangle(OFFSET + BOXSIZE*c + 2,OFFSET + BOXSIZE*r + 2,
                             OFFSET + BOXSIZE*(c+1) - 2, 
                             OFFSET + BOXSIZE*(r+1) - 2,bg);
            sc.drawDisc(OFFSET + BOXSIZE*c + 20, OFFSET + BOXSIZE*r + 20, 
                        10, Color.pink);
            sc.drawRectangle(OFFSET + BOXSIZE*c + 16,OFFSET + BOXSIZE*r + 16,
                             OFFSET + BOXSIZE*c + 25,OFFSET + BOXSIZE*r + 25, bg);
        }
    }
    
    /**
     * Responds to a mouse click. 
     * If it's on the board, make the appropriate move and update the screen display. 
     * If it's on SOLVED?,   check the solution and display the result. 
     * If it's on CLEAR,     clear the puzzle and update the screen display. 
     */
    public void mousePressed(MouseEvent e) 
    {
        if (OFFSET <= e.getX() && e.getX() <= OFFSET + BOXSIZE*size && 
            OFFSET <= e.getY() && e.getY() <=  OFFSET + BOXSIZE*size){
            int c = (int)((e.getX()-OFFSET)/BOXSIZE);
            int r = (int)((e.getY()-OFFSET)/BOXSIZE);
            if (SwingUtilities.isLeftMouseButton(e))
                puzzle.leftClick(r, c);
            else
            if (SwingUtilities.isRightMouseButton(e))
                puzzle.rightClick(r, c);
            updateSquare(r,c);
        }    
        if (OFFSET <= e.getX() && e.getX() <=  OFFSET + 90 && 
            OFFSET + BOXSIZE*size + 8 <= e.getY() && 
            e.getY() <= OFFSET + BOXSIZE*(size+1)){
            sc.drawRectangle(0,OFFSET + (size+1)*BOXSIZE, WINDOWSIZE, WINDOWSIZE,bg);
            sc.drawString(CheckSolution.isSolution(puzzle),OFFSET,
                          OFFSET + BOXSIZE*(size+2)-20,writing);
        }
        if (OFFSET + BOXSIZE*(size-2) + 11 <= e.getX() && 
            e.getX() <= OFFSET + BOXSIZE*size && 
            OFFSET + BOXSIZE*size + 8 <= e.getY() && 
            e.getY() <= OFFSET + BOXSIZE*(size+1)){
            puzzle.clear();
            sc.drawRectangle(0,OFFSET + (size+1)*BOXSIZE, WINDOWSIZE, WINDOWSIZE,bg);
            for (int i = 0; i < size ; i++)
                for (int j = 0; j < size; j++)
                    updateSquare(i,j);
        }        
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
