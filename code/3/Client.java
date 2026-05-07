import java.rmi.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Enter num1 : ");
            double num1 = sc.nextDouble();

            System.out.println("Enter num2 : ");
            double num2 = sc.nextDouble();

            ServerIntf obj = (ServerIntf) Naming.lookup("rmi://localhost/Server");

            double result = obj.Division(num1, num2);

            System.out.println("Result = " + result);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}