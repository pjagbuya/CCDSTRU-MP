import java.util.Set;

import javax.swing.Renderer;

import java.util.HashSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

// Model Classes
import Input.PlayerInput;
import models.Coord;
import models.GAME;
import models.Grid;
import models.Winner;
import models.RULES;

// Prettifying Classes
import colorPrint.Paint; 
import SysControls.*;  

import colorPrint.Paint;    // Paul - Painting text tools are here
import SysControls.*;       // Paul - PAUSE and CLS are here
import screenRender.Colors;
import screenRender.ScreenRenderer;
// NOTE: 
// Each coordinate is defined as (row, col) or (y, x) to be akin to array
// indexing. Hence, (2, 1) is the cell at row 2 and column 1 and not the
// other way around.

public class MP {
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
    static char[][] newGridArray = new char[10][19];
    static int[][] gridColors = new int[10][19];
    static String errorMessage = "";

    static ScreenRenderer renderer;

    // Saveable Player lives;
    static int[] Alives = new int[]{3};
    static int[] Blives = new int[]{3};
    static int playerALives = 3;
    static int playerBLives = 3;
    /*
     * Free - the set of grid coordinates which don't have a number yet
     */
    static Set<Coord> getFree() {
        Set<Coord> Free = new HashSet<Coord>(S);
        Free.removeAll(Occ);

        return Free;
    }


   // CLARIFICATIONS - added a throw for my CLS class para maclear console print sa terminal
    public static void main(String[] args) throws IOException, InterruptedException {

        // Clears screen text
        CLS cls;
        // Pause for the player to process what happened
        PAUSE pause;

        // Scanner if user wants to go for more rounds
        Scanner sc_Continue = new Scanner(System.in);

        boolean firstGame = true;

        renderer = new ScreenRenderer(96, 30, 4, 3, Colors.GREEN);
        for (int i = 0; i < newGridArray.length; i++) {
            Arrays.fill(newGridArray[i], ' ');
            Arrays.fill(gridColors[i], 0);
        }

        // SET Initialize all the Coordinates and store them in a set
        initRowAndColSets();
        RULES.displayRules();

        while(true){

            // initialize winner as none
            Winner winner = Winner.NONE;

            // Start Game or random game
            if (firstGame || decideGame()) {
                winner = startGridGame();



            } else {
                winner = startRiddleGame();

                // Results of the riddle game are reflected
                playerBLives = Blives[0];
                playerALives = Alives[0];
            }  

            // Check if start riddle game or not
            firstGame = false;
            // Evaluate the result
            if (winner == Winner.PLAYER_A) {
                playerBLives -= 1;
                Blives[0] = playerBLives;
            } else if (winner == Winner.PLAYER_B){
                playerALives -= 1;
                Alives[0] = playerALives;
            }

        

            // Congratulatory menu when one ofe the player's lives are done
            if (playerALives == 0 || playerBLives == 0) {
                // ERASE THIS IF UPDATE DECORATION:
                // System Congratulates Winner ASCII art
                System.out.println();
                System.out.println();
                System.out.println(Paint.paintTextGreen("Updated Score List: "));
                System.out.println();
                displayPlayerPts(playerALives, playerBLives);
                System.out.println();
                Paint.paintWinner(winner.getResponse());
    
                // Pause for the player to process what happened
                Paint.turnOnYellow();
                pause = new PAUSE();
                Paint.turnOffColor();
                
                System.out.println();
                System.out.println("New Game? (Y/N): ");
    
                String isContinue = sc_Continue.nextLine();
                if (isContinue.contains("Y") || isContinue.contains("y")) {
                    cls = new CLS();
    
                    playerALives = 3;
                    Blives[0] = 3;
                    playerBLives = 3;
                    Alives[0] = 3;
                    firstGame = true;
                } else {
                    System.out.println(
                        "GOODBYE! JAA NE! SAYONARA! ADIOS! MATA NEE! BID ADIEU! BAIBAI! PAALAM! THANK YOU!");
                    break;
                }
            } 
            


            else {
                System.out.println();
                if(winner == Winner.NONE)
                {
                    System.out.println(winner.getResponse());
                    System.out.println(Paint.paintTextYellow("DRAW! has occured, no one gets anything."));
                }else{
                    Paint.paintRoundWinner(winner.getResponse());
                }
                
                
                System.out.println();
                System.out.println();
                System.out.println(Paint.paintTextGreen("Updated Scores Below: "));
                System.out.println();
                System.out.println();
                displayPlayerPts(playerALives, playerBLives);

    
                // TODO: display current score standings
    
                System.out.println("Press enter to move to the next round.");
                sc_Continue.nextLine();
            }
    
            
        }
        
        sc_Continue.close();
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

    // Adds a checker if the move was valid or not
    static Boolean isInvalidMove = false;
    static boolean decideGame() {
        // Idk if this is a uniform 60-40 split but it should work anyway
        return new Random().nextInt(100) < 60;
    }

    // Added a throw for the CLS class para maclear console print sa terminal.
    static Winner startGridGame() throws IOException, InterruptedException {
        resetGridVars();
        
        



        // ERASE THIS IF UPDATE DECORATION:
        // Draws and display an empty array
        boolean isOnceDraw = false;

        // Clears screen text
        CLS cls = new CLS();

        // Pauses
        PAUSE pause;

        // Added playerB here but since there is a next player func 
        // i think we can cancel this
        PlayerInput playerInputB;
        PlayerInput playerInputA;

        

        Winner winner = Winner.NONE;
        initRowAndColSets();
        

        
        while (winner == Winner.NONE) {


            renderer.initScreen(Colors.GREEN);
            initScreen();
            if(errorMessage.length() != 0) {
                renderer.setPixels(60, 11, errorMessage, Colors.RED);
            }

            // `pos` refers to the coordinate in which to place the number `peg`
            // on the grid.
            

            // ERASE THIS IF UPDATE DECORATION - Draws and display an empty array
            if (!isOnceDraw){
                Grid.drawGrid(gridArray, newGridArray);
                renderGame();
                isOnceDraw = true;
            }
            


            /* Only activate when it is player A's turn
             * Only activate when there is no Winner
             */
            if (turn && winner == Winner.NONE){

                
                // Prompt Player A for input
                playerInputA = PlayerInput.promptInput(turn,  renderer, 10, 11);

                

                // Ready screen and display the result
                cls = new CLS();
                
                // Checks player's input and displays what happens on screen
                NextPlayerMove(playerInputA.getPeg(), playerInputA.getPos());
                if (isInvalidMove) {
                    System.out.println(
                        Paint.paintTextRed(
                            "ERROR! Invalid value of "+ playerInputA.getPeg() + 
                            " and/or invalid coordinates of " + 
                            "(" + playerInputA.getPos().getX() + ", " + playerInputA.getPos().getY() + ")."
                        )
                    );
                    pause = new PAUSE();
                    isInvalidMove = false;
                } else {
                    // Check if player B won
                    over = hasRowOrColumnSumLessThan15() || allRowOrColumnSumsEqual15();
                    winner = GameOver(over);
                }
            }

            // Only activate when it is player B's turn
            else {


                // Checks input for B
                playerInputB = PlayerInput.promptInput(turn, renderer, 10, 11);

                // Clear screen and display the result
                cls = new CLS();

                // Checks player's input and displays what happens on screen
                NextPlayerMove(playerInputB.getPeg(), playerInputB.getPos());

                if(isInvalidMove) {
                    System.out.println(
                        Paint.paintTextRed(
                            "ERROR! Invalid value of "+ playerInputB.getPeg() + 
                            " and/or invalid coordinates of " + 
                            "(" + playerInputB.getPos().getX() + ", " + playerInputB.getPos().getY() + ")."
                        )
                    );
                    pause = new PAUSE();
                    isInvalidMove = false;
                } else {
                    // Check if player B won
                    over = hasRowOrColumnSumLessThan15() || allRowOrColumnSumsEqual15();
                    winner = GameOver(over);
                }
            }            
        }
        

        return winner;        
    }

    static Winner startRiddleGame() throws IOException, InterruptedException {
        
        return GAME.game(Alives, Blives);
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
        Grid.drawAndSetGrid(gridArray, newGridArray, gridColors, peg, pos, turn);


    }

    // START - NEXT PLAYER CONTROL - START
    static void NextPlayerMove(int peg, Coord pos) {
        Set<Integer> Peg = getPeg();
        Set<Coord> Free = getFree();

        if (Peg.contains(peg) && Free.contains(pos)) {
            ok = true;
            Occ.add(pos);

            // Draws the table if the move is valid
            setGrid_BasedOnMove(peg, pos);
        } else {
            
            isInvalidMove = true;

        }
        Grid.displayGrid(gridArray, newGridArray);
        displayPlayerPts(playerALives, playerBLives);

        if(turn){
            System.out.println(Paint.paintTextCyan("Player A's Turn: "));
            System.out.println();

        }
        else{
            System.out.println(Paint.paintTextOrange("Player B's Turn: "));
            System.out.println();

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
    static void displayPlayerPts(int playerAScore, int playerBScore) {
        // ERASE THIS IF UPDATE DECORATION:
        // Place holder label for player 1
        System.out.println(Paint.paintTextCyan("Player A: " + playerAScore));

        // ERASE THIS IF UPDATE DECORATION:
        // Placeholder label for player 2
        System.out.println(Paint.paintTextOrange("Player B: " + playerBScore));

        System.out.println();
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

    static void resetGridVars() {
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
    static void initScreen() {
        //Constant numbers represent positions in the screen
        String header = "" +
                "░░██╗░░░░░░░██╗░██████╗██╗░░░██╗██████╗░░░░░░░██╗░░██╗░█████╗░██╗░░░░░░░██╗░░\n" +
                "░██╔╝░░██╗░░██║██╔════╝██║░░░██║██╔══██╗░░░░░░╚██╗██╔╝██╔══██╗██║░░██╗░░╚██╗░\n" +
                "██╔╝░██████╗██║╚█████╗░██║░░░██║██║░░██║█████╗░╚███╔╝░██║░░██║██║██████╗░╚██╗\n" +
                "╚██╗░╚═██╔═╝╚═╝░╚═══██╗██║░░░██║██║░░██║╚════╝░██╔██╗░██║░░██║╚═╝╚═██╔═╝░██╔╝\n" +
                "░╚██╗░░╚═╝░░██╗██████╔╝╚██████╔╝██████╔╝░░░░░░██╔╝╚██╗╚█████╔╝██╗░░╚═╝░░██╔╝░\n" +
                "░░╚═╝░░░░░░░╚═╝╚═════╝░░╚═════╝░╚═════╝░░░░░░░╚═╝░░╚═╝░╚════╝░╚═╝░░░░░░░╚═╝░░";

        String p1Turn =
                "█▀█ █░░ ▄▀█ █▄█ █▀▀ █▀█   ▄▀█ ▀ █▀   ▀█▀ █░█ █▀█ █▄░█\n" +
                        "█▀▀ █▄▄ █▀█ ░█░ ██▄ █▀▄   █▀█ ░ ▄█   ░█░ █▄█ █▀▄ █░▀█";

        String p2Turn = "█▀█ █░░ ▄▀█ █▄█ █▀▀ █▀█   █▄▄ ▀ █▀   ▀█▀ █░█ █▀█ █▄░█\n" +
                "█▀▀ █▄▄ █▀█ ░█░ ██▄ █▀▄   █▄█ ░ ▄█   ░█░ █▄█ █▀▄ █░▀█";

        if(turn) {
            renderer.setPixels(21, 8, p1Turn, Colors.CYAN);
        } else {
            renderer.setPixels(21, 8, p2Turn, Colors.ORANGE);
        }
        renderer.setPixels(9, 1, header, Colors.ORANGE);
        renderer.createHorizontalLine(22, 3, Colors.GREEN);
        renderer.createVerticalLine(44, 24, 4, 7, Colors.GREEN);
        renderer.setPixels(38, 10, newGridArray, Colors.OFF_COLOR);
        //Score of players
        renderScore(playerALives, playerBLives);

        for (int i = 0; i < gridColors.length; i++) {
            for (int j = 0; j < gridColors[i].length; j++) {
                Colors color = gridColors[i][j] == 1? Colors.CYAN : gridColors[i][j] == -1 ? Colors.ORANGE : Colors.OFF_COLOR;
                renderer.setColorOfPixel(38 + j, 10 + i, color);
            }
        }
    }

    static void renderGame() {
        initScreen();
        renderer.renderScreen();
    }

    static void renderScore(int playerAScore, int playerBScore) {
        String p1 =
                "▒█▀▀█ █░░ █░░█ █▀▀█   ░█▀▀█ ▄ \n" +
                "▒█▄▄█ █░░ █▄▄█ █▄▄▀   ▒█▄▄█ ░ \n" +
                "▒█░░░ ▀▀▀ ▄▄▄█ ▀░▀▀   ▒█░▒█ ▀";

        String p2 =
                "▒█▀▀█ █░░ █░░█ █▀▀█   ▒█▀▀█ ▄ \n" +
                "▒█▄▄█ █░░ █▄▄█ █▄▄▀   ▒█▀▀▄ ░ \n" +
                "▒█░░░ ▀▀▀ ▄▄▄█ ▀░▀▀   ▒█▄▄█ ▀";

        renderer.setPixels(1, 26, p1, Colors.CYAN);
        renderer.setPixels(31, 26, intToAsciiArt(playerAScore), Colors.CYAN);
        renderer.setPixels(50, 26, p2, Colors.ORANGE);
        renderer.setPixels(80, 26, intToAsciiArt(playerBScore), Colors.ORANGE);
    }

    static String intToAsciiArt(int number) {
        StringBuilder[] finalArt = new StringBuilder[3];
        String[] numbers =
                ("█▀▀█ ▄█░ █▀█ █▀▀█ ░█▀█░ █▀▀ ▄▀▀▄ ▀▀▀█ ▄▀▀▄ ▄▀▀▄ \n" +
                 "█▄▀█ ░█░ ░▄▀ ░░▀▄ █▄▄█▄ ▀▀▄ █▄▄░ ░░█░ ▄▀▀▄ ▀▄▄█ \n" +
                 "█▄▄█ ▄█▄ █▄▄ █▄▄█ ░░░█░ ▄▄▀ ▀▄▄▀ ░▐▌░ ▀▄▄▀ ░▄▄▀ ").split("\n");



        int locations[] = {0, 5, 9, 13, 18, 24, 28, 33 ,38 ,43};
        int width[] = {4, 3, 3, 4, 5, 3, 4, 4, 4, 4};

        char[] nums = ("" + number).toCharArray();

        for (int i = 0; i < 3; i++) {
            finalArt[i] = new StringBuilder();
        }


        for (int i = 0; i < nums.length; i++) {
            int startString = locations[Integer.parseInt("" + nums[i])];
            int endString = startString + width[Integer.parseInt("" + nums[i])];
            finalArt[0].append(numbers[0], startString, endString + 1);
            finalArt[1].append(numbers[1], startString, endString + 1);
            finalArt[2].append(numbers[2], startString, endString + 1);
        }
        finalArt[0].append("\n");
        finalArt[1].append("\n");
        return String.join("", finalArt);
    }
}