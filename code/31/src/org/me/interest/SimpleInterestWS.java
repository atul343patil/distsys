package org.me.interest;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "SimpleInterestWS")
public class SimpleInterestWS {

    // Method to calculate Simple Interest: SI = (P * R * T) / 100
    @WebMethod(operationName = "calculateSI")
    public double calculateSI(
            @WebParam(name = "principal") double principal,
            @WebParam(name = "rate")      double rate,
            @WebParam(name = "time")      double time) {
        // Apply simple interest formula and return result
        return (principal * rate * time) / 100;
    }

    // Method to calculate Total Amount: A = P + SI
    @WebMethod(operationName = "calculateTotal")
    public double calculateTotal(
            @WebParam(name = "principal") double principal,
            @WebParam(name = "rate")      double rate,
            @WebParam(name = "time")      double time) {
        // Calculate SI first then add principal
        double si = (principal * rate * time) / 100;
        return principal + si;
    }
}