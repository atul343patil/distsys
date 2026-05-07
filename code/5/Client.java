import java.rmi.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Enter number : ");
            double num = sc.nextDouble();

            ServerIntf obj = (ServerIntf) Naming.lookup("rmi://localhost/Server");

            double result = obj.Power(num);

            System.out.println("Result = " + result);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}