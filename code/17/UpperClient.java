import UpperModule.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import java.util.Scanner;

class UpperClient {

    public static void main(String args[]) {

        try {

            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");

            NamingContextExt ncRef =
                    NamingContextExtHelper.narrow(objRef);

            Upper obj = UpperHelper.narrow(
                    ncRef.resolve_str("Upper"));

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter string: ");

            String input = sc.nextLine();

            String result = obj.to_uppercase(input);

            System.out.println("Uppercase string: " + result);

        } catch (Exception e) {

            System.out.println("Error: " + e);
        }
    }
}