package models;

import java.util.Set;



import java.util.HashSet;


import colorPrint.Paint;
import screenRender.ScreenRenderer;

public class Grid {
    // Set of all coordinates in the grid
    private static Set<Coord> S;

    private Set<Coord> free;

    // Rows
    private Set<Integer> one;
    private Set<Integer> two;
    private Set<Integer> three;

    // Cols
    private Set<Integer> four;
    private Set<Integer> five;
    private Set<Integer> six;

    public Grid() {
        initS();

        one = new HashSet<Integer>();
        two = new HashSet<Integer>();
        three = new HashSet<Integer>();

        four = new HashSet<Integer>();
        five = new HashSet<Integer>();
        six = new HashSet<Integer>();
    }

    void initS() {
        S = new HashSet<Coord>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                S.add(new Coord(i + 1, j + 1));
            }
        }
    }

    public Set<Integer> getOne() {
        return one;
    }

    public Set<Integer> getTwo() {
        return two;
    }

    public Set<Integer> getThree() {
        return three;
    }

    public Set<Integer> getFour() {
        return four;
    }

    public Set<Integer> getFive() {
        return five;
    }

    public Set<Integer> getSix() {
        return six;
    }

    public Set<Coord> getOcc() {
        Set<Coord> Occ = new HashSet<Coord>(S);
        Occ.removeAll(free);

        return Occ;
    }

    // Newly added
    public static String getValueAtCoord(int x, int y, Set<Coord> occupiedCoords) {
        for (Coord coord : occupiedCoords) {
            if (coord.getX() == x && coord.getY() == y) {
                return String.valueOf(coord.getX()) + ", "+ String.valueOf(coord.getY());
            }
        }
        return " ";
    }

    // Newly added - prints Coords on screen // This is useless just wanted to gauge how the functions worked, you can delete
    public static void printGrid(Set<Coord> occupiedCoords) {


        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if(getValueAtCoord(i, j, occupiedCoords) != null)
                {
                    System.out.print("| ");
                    System.out.print("\033[1;36m");
                    System.out.print(getValueAtCoord(i, j, occupiedCoords) + " ");
                    System.out.print("\033[0m");
                    System.out.print("| ");

                }
                
            }
            System.out.println();
            System.out.println();
        }
    }


    // ERASE THIS IF UPDATE DECORATION - Just display the content of the grid
    public static void displayGrid(String[][] grid)
    {
        for (int i = 1; i <= 9; i++) {
            
            for (int j = 1; j <= 18; j++) {

                System.out.print(grid[i][j]);
            }

            System.out.println();
        }


    }

    // ERASE THIS IF UPDATE DECORATION - Empties the grid and displays it as well
    public static void drawGrid(String[][] grid){

        System.out.print("\033[1;33m        L     C      R\033[0m");
   
        System.out.println();
        
        for (int i = 1; i <= 9; i++) {
            
            for (int j = 1; j <= 18; j++) {


                // Labelling
                if (i == 2 && j == 1)
                {
                    System.out.print("\033[1;33m  T   \033[0m"); 
                }
                else if (i == 5 && j == 1)
                {
                    System.out.print("\033[1;33m  M   \033[0m"); 
                }
                else if (i == 8 && j == 1)
                {
                    System.out.print("\033[1;33m  B   \033[0m"); 
                }
                else if ((i % 3 == 1 || i % 3 == 0) && j == 1)
                {
                    // Spacing accuracy
                    System.out.print("      "); 
                }

                if(i % 3 == 1)
                {
                    grid[i][j] = "_";
                    System.out.print("_");
                }
                else if (i % 3 ==  2)
                {

                    // Ends of a box
                    if (j % 6 == 1 || j % 6 == 0)
                    {
                        // Ends of a box
                        grid[i][j] = "|";
                        System.out.print("|");
                    }

                    else
                    {
                        // Space in a box
                        grid[i][j] = " ";
                        System.out.print(" ");
                    }
                }
                else if (i % 3 == 0)
                {
                    if(j % 6 == 1 || j % 6 == 0)
                    {
                        grid[i][j] = "+";
                        System.out.print("+");
                    }
                    else
                    {
                        grid[i][j] = "-";
                        System.out.print("-");
                    }
                }
                    

                
                
            }

            System.out.println();
        }

    }
    // If you wanna revers this process just call the function above which should reset and display
    // Newly added just draw a box, might need parameter if want to put coordinate
    // ERASE THIS FOR NEW DECORATION - the string updates based on input, displays it as well
    public static void drawAndSetGrid(String[][] grid, int peg, Coord pos, boolean isPlayerA)
    {

        // System.out.print("\033[1;33m        L     C      R\033[0m");
        
        System.out.println();
        
        for (int i = 1; i <= 9; i++) {


            for (int j = 1; j <= 18; j++) {

                // Labelling
                // if (i == 2 && j == 1)
                // {
                //     System.out.print("\033[1;33m  T   \033[0m"); 
                // }
                // else if (i == 5 && j == 1)
                // {
                //     System.out.print("\033[1;33m  M   \033[0m"); 
                // }
                // else if (i == 8 && j == 1)
                // {
                //     System.out.print("\033[1;33m  B   \033[0m"); 
                // }
                // else if ((i % 3 == 1 || i % 3 == 0) && j == 1)
                // {
                //     // Spacing accuracy
                //     System.out.print("      "); 
                // }
                
                // Middle layer of a box
                if (i % 3 ==  2)
                {   
                    

                    // if (j % 6 == 1 || j % 6 == 0)
                    // {
                    //     // Ends of a box
     
                    //     // System.out.print(grid[i][j]);
                    // }
                    // Content in the middle
                    if (j % 6 == 3)
                    {
                        
                        // Labelling with colors
                        // We follow what the specs seem to indicate (1,1) in programming sense is top left
                        if(isPlayerA && pos.getY() == (j/6+1) &&    // +1 because we aint starting at index 0 
                           pos.getX() == (i/3+1))
                        {
                            
                            grid[i][j] = Paint.paintTextCyan(Integer.toString(peg));
                            // System.out.print(grid[i][j]);
                        }
                        else if (!isPlayerA && pos.getY() == (j/6+1) &&
                                 pos.getX() == (i/3+1))
                        {
                            grid[i][j] = Paint.paintTextOrange(Integer.toString(peg));
                            // System.out.print(grid[i][j]);

                        }
                        // else
                        // {
                        //     pass
                        //     // System.out.print(grid[i][j]);
                        // }
                       


                    }
                    
                    // Put empty or free
                    // else
                    // {
                    //     // Space in a box
                    //     // System.out.print(" ");
                    // }
                }
                // else 
                // {
                //     // System.out.print(grid[i][j]);
                // }
                    

                
                
            }

            // System.out.println();
        }

    }

    public void placeNumber(int number, Coord coords) {
        // ...

    }
}