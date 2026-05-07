import java.rmi.*;

public interface ServerIntf extends Remote {
    public int CountVowels(String str) throws RemoteException;
}