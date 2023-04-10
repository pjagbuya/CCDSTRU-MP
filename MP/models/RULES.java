package models;
import colorPrint.Paint;
import SysControls.*;

// Call RULES.displayRules() to display to user on screen of the Game's Rules, Mechanics, and Conditions
public class RULES {

    private static void displayTitle(){
        String art = "\t$$$$$$$\\  $$\\   $$\\ $$\\       $$$$$$$$\\  $$$$$$\\  \n" +
                     "\t$$  __$$\\ $$ |  $$ |$$ |      $$  _____|$$  __$$\\ \n" +
                     "\t$$ |  $$ |$$ |  $$ |$$ |      $$ |      $$ /  \\__|\n" +
                     "\t$$$$$$$  |$$ |  $$ |$$ |      $$$$$\\    \\$$$$$$\\  \n" +
                     "\t$$  __$$< $$ |  $$ |$$ |      $$  __|    \\____$$\\ \n" +
                     "\t$$ |  $$ |$$ |  $$ |$$ |      $$ |      $$\\   $$ |\n" +
                     "\t$$ |  $$ |\\$$$$$$  |$$$$$$$$\\ $$$$$$$$\\ \\$$$$$$  |\n" +
                     "\t\\__|  \\__| \\______/ \\________|\\________| \\______/ \n";
        
        Paint.turnOnYellow();
        System.out.println(art);
        Paint.turnOffColor();
    }

    private static void displayTableCoords(){
        String[][] coordinates = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                coordinates[i][j] = Paint.paintTextYellow("(" + (i + 1) + ", " + (j + 1) + ")");
            }
        }

        System.out.println(Paint.paintTextYellow("\t\t        L       C        R     "));
        System.out.println("\t\t   +--------+--------+--------+");
        for (int i = 0; i < 3; i++) {
            System.out.print("\t\t");
            for (int j = 0; j < 3; j++) {
                if(i == 0 && j == 0){
                    System.out.print(Paint.paintTextYellow("T  "));
                }
                else if(i == 1 && j == 0){
                    System.out.print(Paint.paintTextYellow("M  "));
                }
                else if(i == 2 && j == 0){
                    System.out.print(Paint.paintTextYellow("B  "));
                }


                System.out.print("| " + coordinates[i][j] + " ");
            }
            System.out.println("|");
            System.out.println("\t\t   +--------+--------+--------+");
        }
    }
    
    public static void displayRules(){

        PAUSE pause;

        displayTitle();
        System.out.println();
        System.out.println();
        System.out.println("\t1.) The game is Called Fifth Fact Foe, Both player start with three lives, labels are \033[1;36mPlayer A\033[0m and \033[1;38;5;202mPlayer B\033[0m");
        System.out.println();
        System.out.println("\t2.) Every game round, players are subjected to a 3x3 table labeled with the following coordinates");
        System.out.println();
        System.out.println();
        displayTableCoords();
        System.out.println();
        System.out.println();
        System.out.println("\t3.) Players can \033[1;33monly choose from (1-9)\033[0m to put in the 3x3 table and they are \033[1;33mNOT allowed to pick the same number\033[0m in a game round");
        System.out.println();
        System.out.println("\t4.) \033[1;36mPlayer A\033[0m and \033[1;38;5;202mPlayer B\033[0m will have sequential turns, \033[1;36mPlayer A\033[0m is always \033[1;33mfirst\033[0m when a Game Round Begins");
        System.out.println();
        System.out.println("\t5.) Next turn of a player will only proceed when the Player inputs valid Coordinates and Number");
        System.out.println();
        System.out.println("\t5.) Whenever an input leads to a \033[1;33msum of either a row or a column to be less than 15\033[0m, the current player automatically loses, the other wins.");
        System.out.println();
        System.out.println("\t6.) Whenever an input leads to a \033[1;33msum of all rows and columns to be equal to 15\033[0m, the current player automatically wins, the other loses.");
        System.out.println();
        System.out.println("\t7.) The Game Round ends when there is a winner, with the loser losing one life");
        System.out.println();
        System.out.println("\t8.) The Game Will End Once a Player's life runs out.");
        System.out.println();
        System.out.println("\t9.) Whenever a round ends, there is a chance to have a RANDOM challenge/riddle/question that could help either player to be at an \033[1;32mADVANTAGE\033[0m or \033[1;31mDISADVANTAGE\033[0m");
        System.out.println();
        System.out.println("\t10.) Draws in these random challenges Mean NO WINNERS, NO advantages given, NO disadvantages given.");
        System.out.println();

        System.out.println();
        pause = new PAUSE();
    }
}
