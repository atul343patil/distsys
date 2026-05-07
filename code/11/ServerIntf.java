import java.rmi.*;

public interface ServerIntf extends Remote {
    public long Factorial(int num) throws RemoteException;
}