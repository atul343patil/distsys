import java.rmi.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Enter first string : ");
            String str1 = sc.nextLine();

            System.out.println("Enter second string : ");
            String str2 = sc.nextLine();

            ServerIntf obj = (ServerIntf) Naming.lookup("rmi://localhost/Server");

            String result = obj.CompareStrings(str1, str2);

            System.out.println("Lexicographically larger string = " + result);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}