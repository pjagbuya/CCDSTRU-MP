package Input;
import models.Coord;
import colorPrint.Paint;    // Paul - Painting text tools are here
import java.util.Scanner;

public class PlayerInput {
    private Coord pos;
    private int peg;

    public PlayerInput(Coord pos, int peg) {
        this.pos = pos;
        this.peg = peg;
    }

    public Coord getPos() {
        return pos;
    }

    public int getPeg() {
        return peg;
    }

    // PAUL - added some boolean turn para malabel kung sinong turn na
    public static PlayerInput promptInput(boolean turn) {
        
        int x, y;
        int value;

        
        Scanner scanner = new Scanner(System.in);

        if(turn){
            System.out.println(Paint.paintTextCyan("Player A's Turn: "));
            System.out.println();
            Paint.turnOnCyan();
        }
        else{
            System.out.println(Paint.paintTextOrange("Player B's Turn: "));
            System.out.println();
            Paint.turnOnOrange();
        }

        System.out.println("Input coord x(1 - 3): ");
        x = scanner.nextInt();


        System.out.println("Input coord y(1 - 3): ");
        y = scanner.nextInt();


        System.out.println("Input value(1 -9 ): ");
        value = scanner.nextInt();

        Paint.turnOffColor();
        return new PlayerInput(new Coord(x, y), value);
   
        
 

       
    }
}

