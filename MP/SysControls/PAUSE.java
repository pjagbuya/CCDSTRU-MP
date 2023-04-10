package SysControls;
import java.util.Scanner;
import colorPrint.Paint;

public class PAUSE {
    public PAUSE(){
        Paint.turnOnYellow();
        System.out.println("Press Any Key To Continue...");
        Paint.turnOffColor();
        Scanner sc = new Scanner(System.in);
        sc.nextLine();



    }
    public static void main(String args[]){
        Paint.turnOnYellow();
        System.out.println("Press Any Key To Continue...");
        Paint.turnOffColor();
        Scanner sc = new Scanner(System.in);
        sc.nextLine();


   }
}
