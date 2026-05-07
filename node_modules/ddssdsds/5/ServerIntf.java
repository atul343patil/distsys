import java.rmi.*;

public interface ServerIntf extends Remote {
    public double Power(double num) throws RemoteException;
}