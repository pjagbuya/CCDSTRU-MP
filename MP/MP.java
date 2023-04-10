import java.util.Set;
import java.util.HashSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

// Model Classes
import Input.PlayerInput;
import models.Coord;
import models.Grid;

// Prettifying Classes
import colorPrint.Paint; 
import SysControls.*;  

// NOTE: 
// Each coordinate is defined as (row, col) or (y, x) to be akin to array
// indexing. Hence, (2, 1) is the cell at row 2 and column 1 and not the
// other way around.

public class MP {

    enum Winner {
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
    static Set<Integer> One = new HashSet<Integer>(); // Row 1
    static Set<Integer> Two = new HashSet<Integer>(); // Row 2
    static Set<Integer> Three = new HashSet<Integer>(); // Row 3

    /* Numbers placed in each column */
    static Set<Integer> Four = new HashSet<Integer>(); // Column 1
    static Set<Integer> Five = new HashSet<Integer>(); // Column 2
    static Set<Integer> Six = new HashSet<Integer>(); // Column 3

    /*
     * Indicates if a turn can proceed, i.e. the number to be placed has not
     * been placed yet
     */
    static boolean ok = false;

    /*
     * Indicates if a number can be placed on the grid, i.e. the space to be 
     * placed on is not occupied yet
     */
    static boolean next = false;
    
    /*
     * Player A's turn if true, Player B's turn otherwise
     */
    static boolean turn = true;

    /*
     * Indicates whether the game is currently over
     */
    static boolean over = true;

    // ERASE THIS IF UPDATE DECORATION:
    // 10 x 19 string 2d string because we will be working on 1-18, so less
    // hassle calcs
    static String[][] gridArray = new String[10][19];

    /*
     * Free - the set of grid coordinates which don't have a number yet
     */
    static Set<Coord> getFree() {
        Set<Coord> Free = new HashSet<Coord>(S);
        Free.removeAll(Occ);

        return Free;
    }

    /*
     * Peg - the numbers which have not yet been placed on the grid
     */
    static Set<Integer> getPeg() {
        Set<Integer> numbersInSets = new HashSet<Integer>();
        numbersInSets.addAll(One);
        numbersInSets.addAll(Two);
        numbersInSets.addAll(Three);
        numbersInSets.addAll(Four);
        numbersInSets.addAll(Five);
        numbersInSets.addAll(Six);

        Set<Integer> Peg = new HashSet<Integer>(P);
        Peg.removeAll(numbersInSets);

        return Peg;
    }

    // MAIN //

    // Added a throw for the CLS class para maclear console print sa terminal.
    public static void main(String[] args) throws IOException, InterruptedException {
        initRowAndColSets();

        // Place holder label for player 1
        System.out.println(Paint.paintTextCyan("Player A: 0"));

        // Placeholder label for player 2
        System.out.println(Paint.paintTextOrange("Player B: 0"));
        System.out.println();

        // ERASE THIS IF UPDATE DECORATION:
        // Draws and display an empty array
        boolean isOnceDraw = false;

        // Added playerB here but since there is a next player func 
        // i think we can cancel this
        PlayerInput playerInputB;
        PlayerInput playerInputA;

        // Scanner if user wants to go for more rounds
        Scanner sc_Continue = new Scanner(System.in);

        Winner winner = Winner.NONE;
        while (winner == Winner.NONE) {
            // ERASE THIS IF UPDATE DECORATION:
            // Draws and display an empty array
            if (!isOnceDraw) {
                Grid.drawGrid(gridArray);
                isOnceDraw = true;
            }
            
            // Only activate when it is player A's turn
            // Only activate when there is no Winner
            if (turn && winner == Winner.NONE) {   
                // Prompt Player A for input
                playerInputA = PlayerInput.promptInput(turn);

                // Ready screen and display the result
                new CLS();
                // Checks player's input and displays what happens on screen
                NextPlayerMove(playerInputA.getPeg(), playerInputA.getPos());

                // Check if player A won
                over = hasRowOrColumnSumLessThan15() || allRowOrColumnSumsEqual15();
                winner = GameOver(over);
            }
      
            // Only activate when it is player B's turn
            // Only activate when there is no Winner
            else if (!turn && winner == Winner.NONE) {
                // Checks input for B
                playerInputB = PlayerInput.promptInput(turn);

                // Clear screen and display the result
                new CLS();

                // Checks player's input and displays what happens on screen
                NextPlayerMove(playerInputB.getPeg(), playerInputB.getPos());

                //Check if player B won
                over = hasRowOrColumnSumLessThan15() || allRowOrColumnSumsEqual15();
                winner = GameOver(over);
            }

            // If a player wins, congratulate and prompt for more
            if (over) {
                // ERASE THIS IF UPDATE DECORATION:
                // System Congratulates Winner ASCII art
                Paint.paintWinner(winner.getResponse());

                // System responds with who is the winner
                System.out.println();
                System.out.println(winner.getResponse());
                System.out.println();

                // Pause for the player to process what happened
                Paint.turnOnYellow();
                new PAUSE();
                Paint.turnOffColor();
                
                System.out.println();
                System.out.println("New Game? (Y/N): ");

                String isContinue = sc_Continue.nextLine();
                if (isContinue.contains("Y") || isContinue.contains("y")) {
                    // Reset all values
                    winner = Winner.NONE;
                    over = false;
                    // Activate to draw again
                    isOnceDraw = false;
                    resetAllGlobal();
                    
                    // Initialize all rows and cols
                    initRowAndColSets();

                    // Ready screen for next
                    new CLS();
                }

                // Exit the loop and goodbye
                else {
                    System.out.println("GOODBYE! JAA NE! SAYONARA! ADIOS! MATA NEE! BID ADIEU! BAIBAI! PAALAM! THANK YOU!");
                }
            }
        }
        
        sc_Continue.close();
    }

    // MAIN METHODS //

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

    static void NextPlayerMove(int peg, Coord pos) {
        Set<Integer> Peg = getPeg();
        Set<Coord> Free = getFree();

        if (Peg.contains(peg) && Free.contains(pos)) {
            ok = true;
            Occ.add(pos);

            // Draws the table if the move is valid
            setGrid_BasedOnMove(peg, pos);
        } else {
            Grid.displayGrid(gridArray);
            System.out.println(
                Paint.paintTextRed(
                    "ERROR! Invalid value of "+ peg + 
                    " and/or invalid coordinates of " + 
                    "(" + pos.getX() + ", " + pos.getY() + ")."
                )
            );

            displayPlayerPts();
        }

        // Following the flow indicated in the specs, if a number `peg` has
        // not been used yet:
        if (ok) {
            // Check if it can be placed in the row indicated by `pos`,
            if (T.contains(pos)) {
                One.add(peg);
                next = !next;
            } else if (M.contains(pos)) {
                Two.add(peg);
                next = !next;
            } else {
                Three.add(peg);
                next = !next;
            }
            
            // And if so, place it in the column set as well.
            if (next) {
                if (L.contains(pos)) {
                    Four.add(peg);
                    next = !next;
                    ok = !ok;
                } else if (C.contains(pos)) {
                    Five.add(peg);
                    next = !next;
                    ok = !ok;
                } else {
                    Six.add(peg);
                    next = !next;
                    ok = !ok;
                }
            }
        }
    }

    // Helper Methods //

    // TO BE MODIFIED:
    // modify/add parameters to this so that the players can update their score
    static void displayPlayerPts(){
        // ERASE THIS IF UPDATE DECORATION:
        // Place holder label for player 1
        System.out.println(Paint.paintTextCyan("Player A: 0"));

        // ERASE THIS IF UPDATE DECORATION:
        // Placeholder label for player 2
        System.out.println(Paint.paintTextOrange("Player B: 0"));

        System.out.println();
    }

    // ERASE THIS IF UPDATE DECORATION:
    // Function void updates the drawing
    static void setGrid_BasedOnMove(int peg, Coord pos)
    {
        // ERASE THIS IF UPDATE DECORATION:
        // The visual indicator of the move, need to update this based on
        // NextPlayerMove
        Grid.drawAndSetGrid(gridArray, peg, pos, turn);
        displayPlayerPts();
    }

    /*
     * Corresponds to the second condition for `over`.
     */
    static boolean allRowOrColumnSumsEqual15() {
        return ((One.size() == 3 && sumSet(One) == 15) &&
                (Two.size() == 3 && sumSet(Two) == 15) &&
                (Three.size() == 3 && sumSet(Three) == 15) &&
                (Four.size() == 3 && sumSet(Four) == 15) &&
                (Five.size() == 3 && sumSet(Five) == 15) &&
                (Six.size() == 3 && sumSet(Six) == 15));
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

    /*
     * Corresponds to the first condition for `over`.
     */
    static boolean hasRowOrColumnSumLessThan15() {
        return ((One.size() == 3 && sumSet(One) < 15) ||
                (Two.size() == 3 && sumSet(Two) < 15) ||
                (Three.size() == 3 && sumSet(Three) < 15) ||
                (Four.size() == 3 && sumSet(Four) < 15) ||
                (Five.size() == 3 && sumSet(Five) < 15) ||
                (Six.size() == 3 && sumSet(Six) < 15));
    }

    static Set<Integer> initP() {
        Set<Integer> P = new HashSet<>();
        
        for (int i = 1; i < 10; i++) {
            P.add(i);
        }

        return P;
    }

    static Set<Coord> initS() {
        Set<Coord> S = new HashSet<>();

        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                S.add(new Coord(i, j));
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

    static void resetAllGlobal() {
        P = initP();
        S = initS();

        T = new HashSet<>();
        M = new HashSet<>();
        B = new HashSet<>();

        L = new HashSet<>();
        C = new HashSet<>();
        R = new HashSet<>();

        Occ = new HashSet<Coord>();

        One = new HashSet<Integer>();
        Two = new HashSet<Integer>();
        Three = new HashSet<Integer>();

        Four = new HashSet<Integer>();
        Five = new HashSet<Integer>();
        Six = new HashSet<Integer>();

        ok = false;
        next = false;
        turn = true;
        over = false;

        // ERASE THIS IF UPDATE DECORATION:
        gridArray = new String[10][19];
    }
}