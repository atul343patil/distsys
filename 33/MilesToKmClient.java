import org.me.converter.MilesToKmWS;
import org.me.converter.MilesToKmWS_Service;
import java.util.Scanner;

public class MilesToKmClient {
    public static void main(String[] args) {
        try {
            // Connect to the SOAP Web Service
            MilesToKmWS_Service service = new MilesToKmWS_Service();
            MilesToKmWS port = service.getMilesToKmWSPort();
            
            // Take input from the user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter distance in Miles: ");
            double miles = scanner.nextDouble();
            
            // Invoke the remote web service method
            double km = port.milesToKilometers(miles);
            
            // Print the response
            System.out.println("Web Service Response: " + miles + " Miles is equal to " + km + " Kilometers.");
            
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}