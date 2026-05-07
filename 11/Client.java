import java.rmi.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Enter number : ");
            int num = sc.nextInt();

            ServerIntf obj = (ServerIntf) Naming.lookup("rmi://localhost/Server");

            long result = obj.Factorial(num);

            System.out.println("Factorial = " + result);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}