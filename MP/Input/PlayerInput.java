package Input;
import models.Coord;
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

    public static PlayerInput promptInput() {
        
        int x, y;
        int value;

        
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input coord x(1 - 3): ");
        x = scanner.nextInt();


        System.out.println("Input coord y(1 - 3): ");
        y = scanner.nextInt();


        System.out.println("Input value(1 -9 ): ");
        value = scanner.nextInt();

        return new PlayerInput(new Coord(x, y), value);
   
        
 

       
    }
}

