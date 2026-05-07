// Import the generated stub classes (created by wsimport)
import org.me.calculator.CalculatorWS;
import org.me.calculator.CalculatorWS_Service;

// Import Scanner to take input from user at runtime
import java.util.Scanner;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            // Create the service object using the generated stub
            CalculatorWS_Service service = new CalculatorWS_Service();

            // Get the port — this is the object used to call remote methods
            CalculatorWS port = service.getCalculatorWSPort();

            // Create scanner to read user input from keyboard
            Scanner scanner = new Scanner(System.in);

            // Ask user to enter first number
            System.out.print("Enter first number: ");
            double a = scanner.nextDouble();

            // Ask user to enter second number
            System.out.print("Enter second number: ");
            double b = scanner.nextDouble();

            // Ask user which operation to perform
            System.out.println("Choose operation: 1-Add  2-Subtract  3-Multiply  4-Divide");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            // Variable to store the result from the web service
            double result = 0;
            String operation = "";

            // Call the appropriate web service method based on user's choice
            switch (choice) {
                case 1:
                    result    = port.add(a, b);
                    operation = "Addition";
                    break;
                case 2:
                    result    = port.subtract(a, b);
                    operation = "Subtraction";
                    break;
                case 3:
                    result    = port.multiply(a, b);
                    operation = "Multiplication";
                    break;
                case 4:
                    result    = port.divide(a, b);
                    operation = "Division";
                    break;
                default:
                    System.out.println("Invalid choice");
                    return;
            }

            // Print the result returned by the web service
            System.out.println("Web Service Response: " + operation + " of " + a + " and " + b + " = " + result);

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}