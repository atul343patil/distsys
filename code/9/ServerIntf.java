import java.rmi.*;

public interface ServerIntf extends Remote {
    public String CompareStrings(String str1, String str2) throws RemoteException;
}