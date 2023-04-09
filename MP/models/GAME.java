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

        randomNumber = 1;
        CLS cls = new CLS();

        
        if (randomNumber == 1){
            Riddle(Alives, Blives);

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
            if(Twice == 2){
                System.out.println(Paint.paintTextCyan("For Player A: "));
            }
            else if (Twice == 1){
                System.out.println(Paint.paintTextOrange("For Player B: "));
            }
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


            Twice--;
            while(randomNumber == origNum){
                randomNumber = lowerBound + secureRandom.nextInt(upperBound-lowerBound);
            }
        }


        

    }
    
}
