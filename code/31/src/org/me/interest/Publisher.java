package org.me.interest;

import javax.xml.ws.Endpoint;

public class Publisher {
    public static void main(String[] args) {
        // Publish on port 8081 so it does not conflict with 7.1
        String url = "http://localhost:8081/ws/interest";
        System.out.println("Starting Simple Interest Web Service at: " + url);
        Endpoint.publish(url, new SimpleInterestWS());
        System.out.println("Server is running! Do not close this terminal.");
        System.out.println("WSDL available at: " + url + "?wsdl");
    }
}