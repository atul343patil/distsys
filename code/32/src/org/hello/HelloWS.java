package org.me.hello;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "HelloWS")
public class HelloWS {

    // Takes user's name as input and returns Hello greeting
    @WebMethod(operationName = "sayHello")
    public String sayHello(@WebParam(name = "userName") String userName) {
        // If name is empty return default greeting
        if (userName == null || userName.trim().isEmpty()) {
            return "Hello, Guest!";
        }
        // Return greeting with the user's name
        return "Hello " + userName + "!";
    }
}