package Input;
import SysControls.CLS;
import models.Coord;
import colorPrint.Paint;    // Paul - Painting text tools are here
import screenRender.Colors;
import screenRender.ScreenRenderer;

import java.io.IOException;
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
    public static PlayerInput promptInput(boolean turn, ScreenRenderer renderer, int posX, int posY) throws IOException, InterruptedException {
        
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

        CLS cls = new CLS();
        Colors color = turn ? Colors.CYAN : Colors.ORANGE;
        renderer.setPixels(posX, posY, "Input coord x(1 - 3):", color);
        renderer.renderScreen();
        renderer.setTextCursor(posX , posY + 1);
        x = scanner.nextInt();
        renderer.setPixels(posX, posY + 1, x, color);

        cls = new CLS();
        renderer.setPixels(posX, posY + 2, "Input coord y(1 - 3):", color);
        renderer.renderScreen();
        renderer.setTextCursor(posX , posY + 3);
        y = scanner.nextInt();
        renderer.setPixels(posX, posY + 3, y, color);

        cls = new CLS();
        renderer.setPixels(posX, posY + 4, "Input value(1 -9 ):", color);
        renderer.renderScreen();
        renderer.setTextCursor(posX , posY + 5);
        value = scanner.nextInt();
        renderer.setPixels(posX, posY + 5, value, color);


        Paint.turnOffColor();
        return new PlayerInput(new Coord(x, y), value);
    }
}

