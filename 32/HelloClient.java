import org.me.hello.HelloWS;
import org.me.hello.HelloWS_Service;
import java.util.Scanner;

public class HelloClient {
    public static void main(String[] args) {
        try {
            HelloWS_Service service = new HelloWS_Service();
            HelloWS port = service.getHelloWSPort();

            Scanner scanner = new Scanner(System.in);

            // Take user's name as input from keyboard
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            // Call the web service method with the entered name
            String response = port.sayHello(name);

            // Print the greeting returned by the web service
            System.out.println("Web Service Response: " + response);

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}