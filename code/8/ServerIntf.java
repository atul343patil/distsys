import java.rmi.*;

public interface ServerIntf extends Remote {
    public String Echo(String name) throws RemoteException;
}