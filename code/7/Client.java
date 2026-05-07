import java.rmi.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Enter distance in miles : ");
            double miles = sc.nextDouble();

            ServerIntf obj = (ServerIntf) Naming.lookup("rmi://localhost/Server");

            double result = obj.MilesToKm(miles);

            System.out.println("Distance in Kilometer = " + result);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}