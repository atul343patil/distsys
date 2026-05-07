import java.rmi.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Enter your name : ");
            String name = sc.nextLine();

            ServerIntf obj = (ServerIntf) Naming.lookup("rmi://localhost/Server");

            String result = obj.Echo(name);

            System.out.println(result);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}