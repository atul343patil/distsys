import java.rmi.*;
import java.rmi.server.*;

public class ServerImpl extends UnicastRemoteObject implements ServerIntf {

    public ServerImpl() throws RemoteException {
        super();
    }

    public double MilesToKm(double miles) throws RemoteException {
        return miles * 1.609;
    }
}