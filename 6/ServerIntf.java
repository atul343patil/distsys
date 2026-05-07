import java.rmi.*;

public interface ServerIntf extends Remote {
    public double CtoF(double celsius) throws RemoteException;
}