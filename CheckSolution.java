/**
 * CheckSolution is a utility class which can check if
 * a board position in an Aquarium puzzle is a solution.
 *
 * @author Lyndon While & Carmen Leong(22789943) & Miguel Lima(22967427)
 * @version 2020
 */
import java.util.Arrays; 
public class CheckSolution
{
    /**
     * Non-constructor for objects of class CheckSolution
     */
    private CheckSolution(){}
    
    /**
     * Returns the number of water squares in each row of Aquarium puzzle p, top down.
     */
    public static int[] rowCounts(Aquarium p)
    {
        int size = p.getSize();
        int[] rowcounts = new int[size];
        for (int row = 0; row < size ; row++){
            int count = 0;
            for (int column = 0; column < size; column++){
                if (p.getSpaces()[row][column] == Space.WATER)
                    count +=1;
            }
            rowcounts[row] = count;
        }
        return rowcounts;
    }
    
    /**
     * Returns the number of water squares in each column of Aquarium puzzle p, left to right.
     */
    public static int[] columnCounts(Aquarium p)
    {
        int size = p.getSize();
        int[] columncounts = new int[size];
        for (int column = 0; column < size ; column++){
            int count = 0;
            for (int row = 0; row < size; row++){
                if (p.getSpaces()[row][column] == Space.WATER)
                    count +=1;
            }
            columncounts[column] = count;
        }
        return columncounts;
    }
    
    /**
     * Returns a 2-int array denoting the collective status of the spaces 
     * in the aquarium numbered t on Row r of Aquarium puzzle p. 
     * The second element will be the column index c of any space r,c which is in t, or -1 if there is none. 
     * The first element will be: 
     * 0 if there are no spaces in t on Row r; 
     * 1 if they're all water; 
     * 2 if they're all not-water; or 
     * 3 if they're a mixture of water and not-water. 
     */
    public static int[] rowStatus(Aquarium p, int t, int r)
    {
        int[] rowstatus = new int[2];
        rowstatus[1] = -1;  
        int water = 0;
        int nonwater = 0;
        //ArrayList<Space> space = new ArrayList<>();
        for (int column = 0; column < p.getSize(); column++){
            if (p.getAquariums()[r][column] == t){
                rowstatus[1] = column;
                if (p.getSpaces()[r][column] == Space.WATER)
                    water += 1;
                else
                    nonwater += 1;
            }    
        }
        if (rowstatus[1] == -1)
            rowstatus[0] = 0;
        else
        if (water > 0 && nonwater == 0)
            rowstatus[0] = 1;
        else
        if (water == 0 && nonwater > 0)
            rowstatus[0] = 2;
        else
        if (water > 0 && nonwater > 0)
            rowstatus[0] = 3;
        return rowstatus;
    }
    
    /**
     * Returns a statement on whether the aquarium numbered t in Aquarium puzzle p is OK. 
     * Every row must be either all water or all not-water, 
     * and all water must be below all not-water. 
     * Returns "" if the aquarium is ok; otherwise 
     * returns the indices of any square in the aquarium, in the format "r,c". 
     */
    public static String isAquariumOK(Aquarium p, int t)
    {
        for (int r = 0; r < p.getSize() ; r ++){
            int status = rowStatus(p,t,r)[0];
            if (status == 0)
                continue;
            else
            if (status == 3)
                return r + "," + rowStatus(p,t,r)[1];
            else 
            if (r != p.getSize()-1){  
                if(status == 1 && rowStatus(p,t,(r+1))[0] == 2)
                    return r + "," + rowStatus(p,t,r)[1];
            }
        }
        return "";
    }
    
    /**
     * Returns a statement on whether we have a correct solution to Aquarium puzzle p. 
     * Every row and column must have the correct number of water squares, 
     * and all aquariums must be OK. 
     * Returns three ticks if the solution is correct; 
     * otherwise see the LMS page for the expected results. 
     */
    public static String isSolution(Aquarium p)
    {
        int size = p.getSize();
        for (int i = 0; i < size; i++){
            if (rowCounts(p)[i] != p.getRowTotals()[i])
                return ("Row " + i + " is wrong");
            if (columnCounts(p)[i] != p.getColumnTotals()[i])
                return ("Column " + i + " is wrong");
        }
        for (int t = 1; t <= p.getAquariums()[size-1][size-1]; t++)
            if (isAquariumOK(p,t) != "")
                return ("The aquarium at " + isAquariumOK(p,t) + " is wrong");
        return "\u2713\u2713\u2713";
    }
}
