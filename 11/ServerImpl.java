import java.rmi.*;
import java.rmi.server.*;

public class ServerImpl extends UnicastRemoteObject implements ServerIntf {

    public ServerImpl() throws RemoteException {
        super();
    }

    public long Factorial(int num) throws RemoteException {
        long fact = 1;

        for(int i = 1; i <= num; i++) {
            fact = fact * i;
        }
        return fact;
    }
}