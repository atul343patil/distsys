import java.rmi.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Enter a word : ");
            String str = sc.nextLine();

            ServerIntf obj = (ServerIntf) Naming.lookup("rmi://localhost/Server");

            int result = obj.CountVowels(str);

            System.out.println("Number of vowels = " + result);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}