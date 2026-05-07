package org.me.converter;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "MilesToKmWS")
public class MilesToKmWS {
    
    @WebMethod(operationName = "milesToKilometers")
    public double milesToKilometers(@WebParam(name = "miles") double miles) {
        // 1 Mile = 1.60934 Kilometers
        return miles * 1.60934;
    }
}