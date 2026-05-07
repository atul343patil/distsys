// Declare this file belongs to the package org.me.calculator
package org.me.calculator;

// Import annotations needed to define a web service
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

// @WebService marks this class as a SOAP web service
// serviceName is what appears in the WSDL
@WebService(serviceName = "CalculatorWS")
public class CalculatorWS {

    // @WebMethod exposes this method to remote clients
    // @WebParam gives the parameter a name in the SOAP XML message
    @WebMethod(operationName = "add")
    public double add(@WebParam(name = "a") double a,
                      @WebParam(name = "b") double b) {
        // Return sum of two numbers
        return a + b;
    }

    @WebMethod(operationName = "subtract")
    public double subtract(@WebParam(name = "a") double a,
                           @WebParam(name = "b") double b) {
        // Return difference of two numbers
        return a - b;
    }

    @WebMethod(operationName = "multiply")
    public double multiply(@WebParam(name = "a") double a,
                           @WebParam(name = "b") double b) {
        // Return product of two numbers
        return a * b;
    }

    @WebMethod(operationName = "divide")
    public double divide(@WebParam(name = "a") double a,
                         @WebParam(name = "b") double b) {
        // Check for division by zero
        if (b == 0) throw new ArithmeticException("Cannot divide by zero");
        // Return division result
        return a / b;
    }
}