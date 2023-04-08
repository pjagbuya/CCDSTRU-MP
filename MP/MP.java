// import java.util.List; - unused
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
// https://github.com/pjagbuya/CCDSTRU-MP.git
import Input.PlayerInput;
import models.Coord;
import models.Grid;


import colorPrint.Paint;    // Paul - Painting text tools are here
import SysControls.*;       // Paul - PAUSE and CLS are here

public class MP {

    enum Winner {

        // Modified to add some color
        PLAYER_A(Paint.paintTextCyan("A wins")),
        PLAYER_B(Paint.paintTextOrange("B wins")),
        NONE("No winner");

        private String response;

        Winner(String response) {
            this.response = response;
        }

        String getResponse() {
            return response;
        }
    }

    /* All positive integers less than 10 */
    static Set<Integer> P = initP();

    /* All coordinates in the grid */
    static Set<Coord> S = initS();

    /* All coordinates in each row */
    static Set<Coord> T = new HashSet<>(); // Row 1
    static Set<Coord> M = new HashSet<>(); // Row 2
    static Set<Coord> B = new HashSet<>(); // Row 3

    /* All coordinates in each column */
    static Set<Coord> L = new HashSet<>(); // Column 1
    static Set<Coord> C = new HashSet<>(); // Column 2
    static Set<Coord> R = new HashSet<>(); // Column 3

    /* Grid coordinates which aleady have a number */
    static Set<Coord> Occ = new HashSet<Coord>();

    /* Numbers placed in each row */
    static Set<Integer> one = new HashSet<Integer>(); // Row 1
    static Set<Integer> two = new HashSet<Integer>(); // Row 2
    static Set<Integer> three = new HashSet<Integer>(); // Row 3

    /* Numbers placed in each column */
    static Set<Integer> four = new HashSet<Integer>(); // Column 1
    static Set<Integer> five = new HashSet<Integer>(); // Column 2
    static Set<Integer> six = new HashSet<Integer>(); // Column 3

    /*
     * Indicates if a turn can proceed, i.e. the number to be placed has not been
     * placed yet
     */
    static boolean ok = false;

    /*
     * Indicates if a number can be placed on the grid, i.e. the space to be placed
     * on is not occupied yet
     */
    static boolean next = false;

    /* Player A's turn if true, Player B's turn otherwise */
    static boolean turn = true;

    // ERASE THIS IF UPDATE DECORATION - 10 x 19 string 2d string because we will be working on 1-18, so less hassle calcs
    static String[][] gridArray = new String[10][19];


   // CLARIFICATIONS - added a throw for my CLS class para maclear console print sa terminal
    public static void main(String[] args) throws IOException, InterruptedException {
    



        
        
        // SET Initialize all the Coordinates and store them in a set
        initRowAndColSets();

        // SET -Set that the winner is stil no one
        Winner winner = Winner.NONE;

        
         // Added playerB here but since there is a next player func i think we can cancel this
         PlayerInput playerInputB;
         PlayerInput playerInputA;

        // Moved variable declarations outside the while loop
        boolean over = false;


        // Scanner if user wants to go for more rounds
        Scanner sc_Continue = new Scanner(System.in);

        // Clears screen ptext
        CLS cls = new CLS();

        // Pause for the player to process what happened
        PAUSE pause;

        // ERASE THIS IF UPDATE DECORATION - Draws and display an empty array
        boolean isOnceDraw = false;


        // Place holder label for player 1
        System.out.println(Paint.paintTextCyan("Player A: 0"));

        // Placeholder label for player 2
        System.out.println(Paint.paintTextOrange("Player B: 0"));
        System.out.println();

        while (winner == Winner.NONE) {
            // `pos` refers to the coordinate in which to place the number `peg`
            // on the grid.
            

            // ERASE THIS IF UPDATE DECORATION - Draws and display an empty array
            if (!isOnceDraw){
                Grid.drawGrid(gridArray);
                isOnceDraw = true;
            }
            


            /* Only activate when it is player A's turn
             * Only activate when there is no Winner
             */
            if (turn && winner == Winner.NONE){

                
                // Prompt Player A for input
                playerInputA = PlayerInput.promptInput(turn);

                // Ready screen and display the result
                cls = new CLS();
                // Checks player's input and displays what happens on screen
                NextPlayerMove(playerInputA.getPeg(), playerInputA.getPos());


                // Check if player A won
                over = hasRowOrColumnSumLessThan15() || allRowOrColumnSumsEqual15();
                winner = GameOver(over);
            }
      
            
            /* Only activate when it is player B's turn
             * Only activate when there is no Winner
             */
            else if(!turn && winner == Winner.NONE)
            {

                // Checks input for B
                playerInputB = PlayerInput.promptInput(turn);

                // Clear screen and display the result
                cls = new CLS();

                // Checks player's input and displays what happens on screen
                NextPlayerMove(playerInputB.getPeg(), playerInputB.getPos());


                 //Check if player B won
                over = hasRowOrColumnSumLessThan15() || allRowOrColumnSumsEqual15();
                winner = GameOver(over);
                
            }


            // If a player wins, congratulate and prompt for more
            if(over){
                // ERASE THIS IF UPDATE DECORATION - System Congratulates Winner ASCII art
                Paint.paintWinner(winner.getResponse());
                // System responds with who is the winner
                System.out.println();
                System.out.println(winner.getResponse());
                System.out.println();


                 // Pause for the player to process what happened
                Paint.turnOnYellow();
                pause = new PAUSE();
                Paint.turnOffColor();
                
                System.out.println();
                System.out.println("New Game? (Y/N): ");

                String isContinue = sc_Continue.nextLine();
                if (isContinue.contains("Y") || isContinue.contains("y")){
                    // Reset all values
                    winner = Winner.NONE;
                    over = false;
                    // Activate to draw again
                    isOnceDraw = false;
                    resetAllGlobal();
                    
                    // Initialize all rows and cols
                    initRowAndColSets();

                    // Ready screen for next
                    cls = new CLS();
                    
                    
                }

                // Exit the loop and goodbye
                else{
                    System.out.println("GOODBYE! JAA NE! SAYONARA! ADIOS! MATA NEE! BID ADIEU! BAIBAI! PAALAM! THANK YOU!");

                }

            }



           
        }
        sc_Continue.close();





    }

    
    
    static void resetAllGlobal(){

        /* All positive integers less than 10 */
        P = initP();

        /* All coordinates in the grid */
        S = initS();

        /* All coordinates in each row */
        T = new HashSet<>(); // Row 1
        M = new HashSet<>(); // Row 2
        B = new HashSet<>(); // Row 3

        /* All coordinates in each column */
        L = new HashSet<>(); // Column 1
        C = new HashSet<>(); // Column 2
        R = new HashSet<>(); // Column 3

        /* Grid coordinates which aleady have a number */
        Occ = new HashSet<Coord>();

        /* Numbers placed in each row */
        one = new HashSet<Integer>(); // Row 1
        two = new HashSet<Integer>(); // Row 2
        three = new HashSet<Integer>(); // Row 3

        /* Numbers placed in each column */
        four = new HashSet<Integer>(); // Column 1
        five = new HashSet<Integer>(); // Column 2
        six = new HashSet<Integer>(); // Column 3

        /*
        * Indicates if a turn can proceed, i.e. the number to be placed has not been
        * placed yet
        */
        ok = false;

        /*
        * Indicates if a number can be placed on the grid, i.e. the space to be placed
        * on is not occupied yet
        */
        next = false;

        /* Player A's turn if true, Player B's turn otherwise */
        turn = true;

        // ERASE THIS IF UPDATE DECORATION - 10 x 19 string 2d string because we will be working on 1-18, so less hassle calcs
        gridArray = new String[10][19];
    }


    /* Grid coordinates which don't have a number yet */
    static Set<Coord> getFree() {

        // original below
        // Set<Coord> Free = new HashSet<Coord>(Occ);

        
        // Free.removeAll(S);


        // CLARIFICATIONS - Paul here, Modified the block above, I think baliktad yung original
        Set<Coord> Free = new HashSet<Coord>(S);
        Free.removeAll(Occ);
        return Free;
    }

    /* Numbers which have not yet been placed on the grid */
    static Set<Integer> getPeg() {

        Set<Integer> numbersInSets = new HashSet<Integer>();
        numbersInSets.addAll(one);
        numbersInSets.addAll(two);
        numbersInSets.addAll(three);
        numbersInSets.addAll(four);
        numbersInSets.addAll(five);
        numbersInSets.addAll(six);

        Set<Integer> Peg = new HashSet<Integer>(P);
        Peg.removeAll(numbersInSets);


    
        return Peg;
    }


    // Main Methods //

    static Winner GameOver(boolean over) {

        if (over) {
            if (turn) {
                if (hasRowOrColumnSumLessThan15())
                    return Winner.PLAYER_B;
                else
                    return Winner.PLAYER_A;
            } else {
                if (hasRowOrColumnSumLessThan15())
                    return Winner.PLAYER_A;
                else
                    return Winner.PLAYER_B;
            }
        } else {
            turn = !turn;
            return Winner.NONE;
        }
    }



    // TO BE MODIFIED - modify/add parameters to this so that the players can update their score
    static void displayPlayerPts(){
        // ERASE THIS IF UPDATE DECORATION - Place holder label for player 1
        System.out.println(Paint.paintTextCyan("Player A: 0"));
        // ERASE THIS IF UPDATE DECORATION - Placeholder label for player 2
        System.out.println(Paint.paintTextOrange("Player B: 0"));
        System.out.println();

    }

    // ERASE THIS IF UPDATE DECORATION - Function void updates the drawing
    static void setGrid_BasedOnMove(int peg, Coord pos)
    {
        // ERASE THIS IF UPDATE DECORATION - The visual indicator of the move, need to update this based on NextPlayerMove
        Grid.drawAndSetGrid(gridArray, peg, pos, turn);
        displayPlayerPts();

    }

    // START - NEXT PLAYER CONTROL - START
    static void NextPlayerMove(int peg, Coord pos) {

        
        
        Set<Integer> Peg = getPeg();
        Set<Coord> Free = getFree();

        // Paul - Should initialize this every time it calls right
        boolean ok = false;


        

        // TODO: contains won't work with pos (I think), probably need another method
        // CLARIFICATIONS - contains should work na, overriden the hashCode() and equals()
        if (Peg.contains(peg) && Free.contains(pos)) {
            ok = true;
            Occ.add(pos);

            // Draws the table if the move is valid
            setGrid_BasedOnMove(peg, pos);
        }
        // Add input validation feedback
        else{
            Grid.displayGrid(gridArray);
            System.out.println(Paint.paintTextRed("ERROR! Invalid value of "+ peg + " and/or invalid coordinates of " + 
            "(" + pos.getX() + ", " + pos.getY() + ")." ));

            // Displays player score
            displayPlayerPts();
        }

        // OK when peg is a number not chosen, pos, a space not occupied

        // TODO: use Set.contains() instead of the below since it
        // "cannot find symbol"

        // also don't forget to access indiv coords when checking since
        // the sets don't contain coords
        // NEWLY MODIFIED, modified the Coord.java and overriden the hashCode() and equals() func, so contains should work na
        if (ok) {

            if (T.contains(pos)) {

                one.add(peg);
                next = !next;
            }

            else if (M.contains(pos))
             {
                two.add(peg);
                next = !next;
            }

            else {
                three.add(peg);
                next = !next;
            }

            // START - IF - START
            if (next) {
                if (L.contains(pos)) 
                {
                    four.add(peg);
                    next = !next;
                    ok = !ok;
                }
                 else if (C.contains(pos)) {
                    five.add(peg);
                    next = !next;
                    ok = !ok;
                } else {
                    six.add(peg);
                    next = !next;
                    ok = !ok;
                }

            }
            // END - IF - END

        }

    }
    // END - NEXT PLAYER CONTROL - END

    // Helper Methods //

    /* Corresponds to the second condition for `over`. */
    static boolean allRowOrColumnSumsEqual15() {


        return ((one.size() == 3 && sumSet(one) == 15) &&
                (two.size() == 3 && sumSet(two) == 15) &&
                (three.size() == 3 && sumSet(three) == 15) &&
                (four.size() == 3 && sumSet(four) == 15) &&
                (five.size() == 3 && sumSet(five) == 15) &&
                (six.size() == 3 && sumSet(six) == 15));
    }

    static void initRowAndColSets() {
        // Rows

        T .addAll(Arrays.asList(new Coord(1, 1),
                new Coord(1, 2),
                new Coord(1, 3)));

        M.addAll(Arrays.asList(new Coord(2, 1),
                new Coord(2, 2),
                new Coord(2, 3)));

        B.addAll(Arrays.asList(new Coord(3, 1),
                new Coord(3, 2),
                new Coord(3, 3)));

        // Cols

        L.addAll(Arrays.asList(new Coord(1, 1),
                new Coord(2, 1),
                new Coord(3, 1)));

        C.addAll(Arrays.asList(new Coord(1, 2),
                new Coord(2, 2),
                new Coord(3, 2)));

        R.addAll(Arrays.asList(new Coord(1, 3),
                new Coord(2, 3),
                new Coord(3, 3)));
    }

    /* Corresponds to the first condition for `over`. */
    static boolean hasRowOrColumnSumLessThan15() {

        return ((one.size() == 3 && sumSet(one) < 15) ||
                (two.size() == 3 && sumSet(two) < 15) ||
                (three.size() == 3 && sumSet(three) < 15) ||
                (four.size() == 3 && sumSet(four) < 15) ||
                (five.size() == 3 && sumSet(five) < 15) ||
                (six.size() == 3 && sumSet(six) < 15));
    }

    static Set<Integer> initP() {
        Set<Integer> P = new HashSet<>();
        
        // NEWLY MODIFIED - Peg should only contain 1 - 9
        for (int i = 1; i < 10; i++) {
            P.add(i);
        }

        return P;
    }

    static Set<Coord> initS() {
        Set<Coord> S = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                S.add(new Coord(i + 1, j + 1));
            }
        }

        return S;
    }

    static int sumSet(Set<Integer> set) {
        int sum = 0;

        for (int num : set) {
            sum += num;
        }

        return sum;
    }

}

