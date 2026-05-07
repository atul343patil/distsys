import java.rmi.*;
import java.rmi.server.*;

public class ServerImpl extends UnicastRemoteObject implements ServerIntf {

    public ServerImpl() throws RemoteException {
        super();
    }

    public double Division(double num1, double num2) throws RemoteException {
        if(num2 == 0){
            System.out.println("Cannot divide by zero");
            return 0;
        }
        return num1 / num2;
    }
}