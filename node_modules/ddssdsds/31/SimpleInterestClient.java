import org.me.interest.SimpleInterestWS;
import org.me.interest.SimpleInterestWS_Service;
import java.util.Scanner;

public class SimpleInterestClient {
    public static void main(String[] args) {
        try {
            // Create service using generated stub
            SimpleInterestWS_Service service = new SimpleInterestWS_Service();
            SimpleInterestWS port = service.getSimpleInterestWSPort();

            Scanner scanner = new Scanner(System.in);

            // Take all three inputs from the user
            System.out.print("Enter Principal amount: ");
            double principal = scanner.nextDouble();

            System.out.print("Enter Rate of Interest (% per year): ");
            double rate = scanner.nextDouble();

            System.out.print("Enter Time (in years): ");
            double time = scanner.nextDouble();

            // Call web service method for SI
            double si    = port.calculateSI(principal, rate, time);
            // Call web service method for Total Amount
            double total = port.calculateTotal(principal, rate, time);

            // Print results returned by the web service
            System.out.println("\nWeb Service Response:");
            System.out.println("Simple Interest = " + si);
            System.out.println("Total Amount    = " + total);

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}