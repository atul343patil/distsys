
import java.rmi.*;
import java.rmi.registry.LocateRegistry;  // ✅ ADD THIS

public class Server {
    public static void main(String[] args){
        try{
            LocateRegistry.createRegistry(1099);   // ✅ ADD THIS LINE

            ServerImpl obj = new ServerImpl();
            Naming.rebind("rmi://localhost/Server", obj);
            System.out.println("Server has started .");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}