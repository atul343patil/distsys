import UpperModule.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

class UpperServer {

    public static void main(String args[]) {

        try {

            ORB orb = ORB.init(args, null);

            POA rootpoa = POAHelper.narrow(
                    orb.resolve_initial_references("RootPOA"));

            rootpoa.the_POAManager().activate();

            UpperImpl obj = new UpperImpl();

            org.omg.CORBA.Object ref =
                    rootpoa.servant_to_reference(obj);

            Upper href = UpperHelper.narrow(ref);

            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");

            NamingContextExt ncRef =
                    NamingContextExtHelper.narrow(objRef);

            ncRef.rebind(ncRef.to_name("Upper"), href);

            System.out.println("Server ready...");

            orb.run();

        } catch (Exception e) {

            System.out.println("Error: " + e);
        }
    }
}