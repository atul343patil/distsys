// Declare package
package org.me.calculator;

// Import Endpoint — used to publish/start the web service
import javax.xml.ws.Endpoint;

public class Publisher {
    public static void main(String[] args) {
        // URL where the web service will be hosted
        String url = "http://localhost:8080/ws/calculator";
        System.out.println("Starting Calculator Web Service at: " + url);
        // Publish the CalculatorWS service at the given URL
        Endpoint.publish(url, new CalculatorWS());
        System.out.println("Server is running! Do not close this terminal.");
        System.out.println("WSDL available at: " + url + "?wsdl");
    }
}