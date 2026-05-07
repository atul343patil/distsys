package org.me.hello;

import javax.xml.ws.Endpoint;

public class Publisher {
    public static void main(String[] args) {
        String url = "http://localhost:8082/ws/hello";
        System.out.println("Starting Hello User Web Service at: " + url);
        Endpoint.publish(url, new HelloWS());
        System.out.println("Server is running! Do not close this terminal.");
        System.out.println("WSDL available at: " + url + "?wsdl");
    }
}