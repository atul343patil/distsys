import java.rmi.*;

public interface ServerIntf extends Remote {
    public double MilesToKm(double miles) throws RemoteException;
}