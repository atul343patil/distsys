import ReverseModule.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import java.util.Scanner;

class ReverseClient {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Reverse obj = ReverseHelper.narrow(ncRef.resolve_str("Reverse"));

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter string: ");
            String input = sc.nextLine();

            String result = obj.reverse_string(input);

            System.out.println("Reversed string: " + result);

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}