// import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
// https://github.com/pjagbuya/CCDSTRU-MP.git
import Input.PlayerInput;
import models.Coord;
import models.Grid;

public class MP {

    enum Winner {
        PLAYER_A("A wins"),
        PLAYER_B("B wins"),
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
    static Set<Coord> T = new HashSet<Coord>(); // Row 1
    static Set<Coord> M = new HashSet<Coord>(); // Row 2
    static Set<Coord> B = new HashSet<Coord>(); // Row 3

    /* All coordinates in each column */
    static Set<Coord> L = new HashSet<Coord>(); // Column 1
    static Set<Coord> C = new HashSet<Coord>(); // Column 2
    static Set<Coord> R = new HashSet<Coord>(); // Column 3

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


    /* Paul here
     * drawAndSetGrid() draws a value and table on the Grid based on what the user wants
     * Follows the logic of the 
     */ 
   
    public static void main(String[] args) {
    

        
        
        initRowAndColSets();

        // 10 x 19 string 2d because we will be working on 1-18, so less hassle calcs
        String[][] gridArray = new String[10][19];

        // Draws and display an empty array
        Grid.drawGrid(gridArray);
        Winner winner = Winner.NONE;
        while (winner == Winner.NONE) {
            // `pos` refers to the coordinate in which to place the number `peg`
            // on the grid.
            
            
            
            // Testing what the following prints cause im dumb
            // Grid.printGrid(T);
            // Grid.printGrid(M);
            // Grid.printGrid(B);
            // Grid.printGrid(L);
            // Grid.printGrid(C);
            // Grid.printGrid(R);

            // Place holder label for player 1
            System.out.println("\033[1;36mPlayer 1: 0\033[0m");
            // Placeholder label for player 2
            System.out.println("\033[1;32mPlayer 2: 0\033[0m");
            System.out.println();
            PlayerInput playerInput1 = PlayerInput.promptInput();
            NextPlayerMove(playerInput1.getPeg(), playerInput1.getPos());

            // The visual indicator of the move
            Grid.drawAndSetGrid(gridArray, playerInput1.getPeg(), playerInput1.getPos(), true);


            // Place holder label for player 1
            System.out.println("\033[1;36mPlayer 1: 0\033[0m");
            // Placeholder label for player 2
            System.out.println("\033[1;32mPlayer 2: 0\033[0m");
            System.out.println();


            PlayerInput playerInput2 = PlayerInput.promptInput();
            NextPlayerMove(playerInput2.getPeg(), playerInput2.getPos());
      
            
            // Visual indicator of the move
            Grid.drawAndSetGrid(gridArray, playerInput2.getPeg(), playerInput2.getPos(), false);
            
            // Place holder label for player 1
            System.out.println("\033[1;36mPlayer 1: 0\033[0m");
            // Placeholder label for player 2
            System.out.println("\033[1;32mPlayer 2: 0\033[0m");
            System.out.println();   


            boolean over = hasRowOrColumnSumLessThan15() || allRowOrColumnSumsEqual15();
            winner = GameOver(over);
        }

        System.out.println(winner.getResponse());
    }

    /* Grid coordinates which don't have a number yet */
    static Set<Coord> getFree() {
        Set<Coord> Free = new HashSet<Coord>(Occ);
        Free.removeAll(S);

        return Free;
    }

    /* Numbers which have not yet been placed on the grid */
    static Set<Coord> getPeg() {
        // Originally Below
        // Set<Integer> numbersInSets = new HashSet<Integer>();
        // numbersInSets.addAll(one);
        // numbersInSets.addAll(two);
        // numbersInSets.addAll(three);
        // numbersInSets.addAll(four);
        // numbersInSets.addAll(five);
        // numbersInSets.addAll(six);

        // Set<Coord> Peg = new HashSet<Coord>(P);
        // Peg.removeAll(numbersInSets);

        // return Peg;

        // Newly added below
        Set<Coord> numbersInSets = new HashSet<Coord>();
        numbersInSets.addAll(convertToCoordSet(one, T));
        numbersInSets.addAll(convertToCoordSet(two, M));
        numbersInSets.addAll(convertToCoordSet(three, B));
        numbersInSets.addAll(convertToCoordSet(four, L));
        numbersInSets.addAll(convertToCoordSet(five, C));
        numbersInSets.addAll(convertToCoordSet(six, R));
    
        Set<Coord> Peg = new HashSet<Coord>(S);
        Peg.removeAll(numbersInSets);
    
        return Peg;
    }

    // Paul -Newly ADDED convers the Coord based on numbers occurences and apply them to coords
    //
    static Set<Coord> convertToCoordSet(Set<Integer> numbers, Set<Coord> coords) {
        Set<Coord> coordSet = new HashSet<Coord>();
        int i = 0;
        for (int number : numbers) {
            int j = 0;

            for (Coord coord : coords) {
                if (i == j) {
                    coordSet.add(coord);
                    break;
                }
                j++;
            }
            i++;
        }
        return coordSet;
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

    // START - NEXT PLAYER CONTROL - START
    static void NextPlayerMove(int peg, Coord pos) {
        Set<Coord> Peg = getPeg();
        Set<Coord> Free = getFree();

        // TODO: contains won't work with pos (I think), probably need another method
        if (Peg.contains(peg) && Free.contains(pos)) {
            ok = true;
            Occ.add(pos);
        }

        // OK when peg is a number not chosen, pos, a space not occupied

        // TODO: use Set.contains() instead of the below since it
        // "cannot find symbol"

        // also don't forget to access indiv coords when checking since
        // the sets don't contain coords
        // Paul here, don't know if this is what you wanted
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

        for (int i = 1; i <= 10; i++) {
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

