package SysControls;

import java.io.IOException;
import java.util.Scanner;

public class Console {
    public static void clear() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public static void pause() {
        System.out.println("Press Any Key To Continue...");
        new Scanner(System.in).nextLine();
    }
}
