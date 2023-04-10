package models;
// import java.util.List; - unused
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
// https://github.com/pjagbuya/CCDSTRU-MP.git
import Input.PlayerInput;
import colorPrint.Paint;    // Paul - Painting text tools are here
import SysControls.*;       // Paul - PAUSE and CLS are here

import java.util.concurrent.ThreadLocalRandom;

import javax.lang.model.util.ElementScanner6;

import java.security.SecureRandom;



// IN order to call game you must pass an array of int of A lives and B lives in the MP.java
// Integrate the lives mechanic in MP
/* Like SO:
 *  static int[] Alives = new int[]{3};
    static int[] Blives = new int[]{3};

    Then when you call it, it will be like GAME.game(Alives, Blives);
 */
public class GAME {



    static int lowerBound = 1;
    static int upperBound = 5;
    static int randomNumber;

    static Scanner sc = new Scanner(System.in);
    static SecureRandom secureRandom = new SecureRandom();

    
   // CLARIFICATIONS - added a throw for my CLS class para maclear console print sa terminal
    public static void game(int[] Alives, int[] Blives) throws IOException, InterruptedException {
        

        // Generates a random number
        // randomNumber = lowerBound + secureRandom.nextInt(upperBound-lowerBound);

        randomNumber = secureRandom.nextInt(8);


        // Two game modes to be tossed around for chacne 67:33 is the probability Riddle:HighLow, Uncomment and apply randomizer
        if (randomNumber % 3 == 0){    
            playHighLow(Alives, Blives);
        }
        else{
            Riddle(Alives, Blives);
        }

        


        
    }
    private static void returnRes(int Twice, int[] Alives,int[] Blives, boolean isAWin){

        if (Twice == 2 && isAWin == true){
            Alives[0] += 1;
            
        }
        else if (Twice == 2 && isAWin == false){
            Alives[0] -= 1;
            
        }
        else if (Twice == 1 && isAWin == true){
            Blives[0] += 1;
            
        }
        else if(Twice == 1 && isAWin == false){
            Blives[0] -= 1;
            
        }
    }
    private static void displayPlayerTurn(int Twice){
        if(Twice == 2){
            System.out.println(Paint.paintTextCyan("For Player A: "));
        }
        else if (Twice == 1){
            System.out.println(Paint.paintTextOrange("For Player B: "));
        }
    }
    public static void displayLives(int[] Alives, int[] Blives){


        System.out.println(Paint.paintTextCyan("Player A: " + Alives[0] + " lives"));
        System.out.println(Paint.paintTextOrange("Player B: " + Blives[0] + " lives"));
 
    }
    public static void Design(){

        Paint.turnOnYellow();

        System.out.println("  _____  _____ _____  _____  _      ______   _______ _____ __  __ ______ ");
        System.out.println(" |  __ \\|_   _|  __ \\|  __ \\| |    |  ____| |__   __|_   _|  \\/  |  ____|");
        System.out.println(" | |__) | | | | |  | | |  | | |    | |__       | |    | | | \\  / | |__   ");
        System.out.println(" |  _  /  | | | |  | | |  | | |    |  __|      | |    | | | |\\/| |  __|  ");
        System.out.println(" | | \\ \\ _| |_| |__| | |__| | |____| |____     | |   _| |_| |  | | |____ ");
        System.out.println(" |_|  \\_\\_____|_____/|_____/|______|______|    |_|  |_____|_|  |_|______|");
    
        Paint.turnOffColor();

    }


    public static void Riddle(int[] Alives, int[] Blives) throws IOException, InterruptedException{

        boolean isAWin;
        int origNum;
        int Twice = 2;
        String sAns;
        int nAns;
        float fAns;

        CLS cls;

        
        randomNumber = lowerBound + secureRandom.nextInt(upperBound-lowerBound);
        origNum = randomNumber;

        isAWin = false;
        while(Twice > 0){
            cls = new CLS();
            Design();
            System.out.println("LIVES COUNT: ");
            displayLives(Alives, Blives);
            System.out.println();
            System.out.println();

            displayPlayerTurn(Twice);
            
            switch(randomNumber){
                case 1:
                    System.out.println();
                    System.out.println("The more you take, the more you leave behind. What am I? ");
                    System.out.println("Input: ");
                    sAns = sc.nextLine();
            
                    if (sAns.equalsIgnoreCase("footsteps") == true || 
                        sAns.equalsIgnoreCase("foots") == true ||
                        sAns.equalsIgnoreCase("steps") == true ||
                        sAns.equalsIgnoreCase("feet")  == true )
                        {
                            Paint.turnOnGreen();
                            System.out.println("You got it right! ");
                            Paint.turnOffColor();
                            isAWin = true;
                        }
                    else{
                        Paint.turnOnRed();
                        System.out.println("You got it wrong");
                        Paint.turnOffColor();
                        isAWin = false;
                    }
    
    
                    break;
    
                case 2:
                    System.out.println();
                    System.out.println("2, 6, 12, 20, 30 ... What is the 10th term?");
                    System.out.println("Input: ");
                    nAns = sc.nextInt();
            
                    if (nAns ==  111)
                        {
                            Paint.turnOnGreen();
                            System.out.println("You got it right! ");
                            Paint.turnOffColor();
                            isAWin = true;
                        }
                    else{
                        Paint.turnOnRed();
                        System.out.println("You got it wrong");
                        Paint.turnOffColor();
                        isAWin = false;
                    }
                break;
                case 3:
                    System.out.println();
                    System.out.println("2, 4, 8, 10, 20, 22 ... What is the next term?");
                    System.out.println("Input: ");
                    nAns = sc.nextInt();
            
                    if (nAns ==  44)
                        {
                            Paint.turnOnGreen();
                            System.out.println("You got it right! ");
                            Paint.turnOffColor();
                            isAWin = true;
                        }
                    else{
                        Paint.turnOnRed();
                        System.out.println("You got it wrong");
                        Paint.turnOffColor();
                        isAWin = false;
                    }
                    break;
                case 4:
                    System.out.println();
                    System.out.println("If three times a number is equal to the number subtracted from 30, what is the number?");
                    System.out.println("Input: ");
                    fAns = sc.nextFloat();
            
                    if (fAns ==  7.5)
                        {
                            Paint.turnOnGreen();
                            System.out.println("You got it right! ");
                            Paint.turnOffColor();
                            isAWin = true;
                        }
                    else{
                        Paint.turnOnRed();
                        System.out.println("You got it wrong");
                        Paint.turnOffColor();
                        isAWin = false;
                    }
                    break;
                case 5:
                    System.out.println();
                    System.out.println("The sum of a number and its double is 18. What is the number?");
                    System.out.println("Input: ");
                    nAns = sc.nextInt();
            
                    if (nAns ==  6)
                        {
                            Paint.turnOnGreen();
                            System.out.println("You got it right! ");
                            Paint.turnOffColor();
                            isAWin = true;
                        }
                    else{
                        Paint.turnOnRed();
                        System.out.println("You got it wrong");
                        Paint.turnOffColor();
                        isAWin = false;
                    }
                    break;
    
            }
            PAUSE pause = new PAUSE();

            returnRes(Twice, Alives, Blives, isAWin);


            Twice--;

            // Ensures next one is a unique and different num for the next player's question
            while(randomNumber == origNum){
                randomNumber = lowerBound + secureRandom.nextInt(upperBound-lowerBound);
            }

        }


        

    }
    private static void displayHighLowTitle(){

        String art = "888    888      8888888       .d8888b.       888    888            .d88888b.  8888888b.            888            .d88888b.       888       888 \n" +
                     "888    888        888        d88P  Y88b      888    888           d88P\" \"Y88b 888   Y88b           888           d88P\" \"Y88b      888   o   888 \n" +
                     "888    888        888        888    888      888    888           888     888 888    888           888           888     888      888  d8b  888 \n" +
                     "8888888888        888        888             8888888888           888     888 888   d88P           888           888     888      888 d888b 888 \n" +
                     "888    888        888        888  88888      888    888           888     888 8888888P\"            888           888     888      888d88888b888 \n" +
                     "888    888        888        888    888      888    888           888     888 888 T88b             888           888     888      88888P Y88888 \n" +
                     "888    888        888        Y88b  d88P      888    888           Y88b. .d88P 888  T88b            888           Y88b. .d88P      8888P   Y8888 \n" +
                     "888    888      8888888       \"Y8888P88      888    888            \"Y88888P\"  888   T88b           88888888       \"Y88888P\"       888P     Y888 \n";
        
        Paint.turnOnYellow();
        System.out.println(art);
        Paint.turnOffColor();
    }
    
    private static void returnUserGuesRes(int nNum, int nGuess){
        PAUSE pause;
        if (nGuess < nNum){
            System.out.println(Paint.paintTextRed("Guess Higher!"));
            System.out.println();
        }
        else if (nGuess > nNum){
            System.out.println(Paint.paintTextMagenta("Guess Lower!"));
            System.out.println();
        }
        else if (nGuess == nNum ){
            System.out.println(Paint.paintTextGreen("You FOUND IT, " + nNum + "!"));
            System.out.println();
            pause = new PAUSE();
        }
    }
    public static void playHighLow(int[] Alives, int[] Blives) throws IOException, InterruptedException{
        int userNumber = -1;
        int guessNumber;

        int nCountG_A = 0;
        int nCountG_B = 0;

        
        // 2 - means Plyaer A
        // 1 - Means Player B
        int Twice = 2;
        PAUSE pause;
        CLS cls = new CLS();
        displayHighLowTitle();

        while(Twice > 0){
            guessNumber = 0 + secureRandom.nextInt(100);
            System.out.println(Paint.paintTextYellow("Guess a number between (0-100), program will state higher or lower to help with your guess"));
            System.out.println(Paint.paintTextYellow("The lesser prompts you take the more likely you get ahead of your opponent"));
            System.out.println();
            
            displayPlayerTurn(Twice);
            while( userNumber!= guessNumber){
                if (Twice == 2){
                    nCountG_A += 1;
                }
                else if (Twice == 1){
                    nCountG_B += 1;
                }
                
                System.out.print(Paint.paintTextYellow("User Input: "));
                Paint.turnOnGreen();
                userNumber = sc.nextInt();
                Paint.turnOffColor();
                returnUserGuesRes(guessNumber, userNumber);
                
            }


            // Next player
            Twice--;

        }

        // Display result
        if(nCountG_A < nCountG_B){

            System.out.println();
            System.out.println(Paint.paintTextCyan("Player A has guessed less with "+ nCountG_A +
            " times, while player B guessed " + nCountG_B + " times"));
            System.out.println(Paint.paintTextCyan("PLAYER A WINS LIVES!"));
            // A wins therefore add life
            returnRes(2, Alives, Blives, true);

            // B loses therefore minus life
            returnRes(1, Alives, Blives, false);
        }
        else if (nCountG_B < nCountG_A){
            System.out.println();
            System.out.println(Paint.paintTextOrange("Player B has guessed less with "+ nCountG_B +
            " times, while player A guessed " + nCountG_A + " times"));
            System.out.println(Paint.paintTextOrange("PLAYER B WINS LIVES!"));


            // A wins therefore add life
            returnRes(2, Alives, Blives, false);

            // B loses therefore minus life
            returnRes(1, Alives, Blives, true);
        }
        else{
            System.out.println("A DRAW HAS OCCURED! Lives remain the same");
        }
        displayLives(Alives, Blives);
        pause = new PAUSE();
        
        
    }
}
