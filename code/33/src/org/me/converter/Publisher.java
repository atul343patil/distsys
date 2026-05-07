package org.me.converter;
import javax.xml.ws.Endpoint;

public class Publisher {
    public static void main(String[] args) {
        String url = "http://localhost:8080/ws/converter";
        System.out.println("Starting Converter Web Service at: " + url);
        Endpoint.publish(url, new MilesToKmWS());
        System.out.println("Server is running! Do not close this terminal.");
    }
}