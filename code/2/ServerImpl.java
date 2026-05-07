import java.rmi.*;
import java.rmi.server.*;

public class ServerImpl extends UnicastRemoteObject implements ServerIntf {

    public ServerImpl() throws RemoteException {
        super();
    }

    public double Multiplication(double num1, double num2) throws RemoteException {
        return num1 * num2;
    }
}