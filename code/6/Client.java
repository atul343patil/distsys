import java.rmi.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Enter temperature in Celsius : ");
            double celsius = sc.nextDouble();

            ServerIntf obj = (ServerIntf) Naming.lookup("rmi://localhost/Server");

            double result = obj.CtoF(celsius);

            System.out.println("Temperature in Fahrenheit = " + result);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}