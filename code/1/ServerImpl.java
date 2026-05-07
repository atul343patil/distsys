// import java.rmi.*;
// import java.rmi.server.*;

// public class ServerImpl extends UnicastRemoteObject implements ServerIntf {

//     public ServerImpl() throws RemoteException {
//         super();
//     }

//     public double Addition(double num1, double num2) throws RemoteException {
//         return num1 + num2;
//     }
// }








import java.rmi.*;
import java.rmi.server.*;

public class ServerImpl extends UnicastRemoteObject implements ServerIntf{
    public ServerImpl() throws RemoteException{
        super();
    }
    public double Addition(double num1,double num2) throws RemoteException{
        return num1+num2;
    }
}