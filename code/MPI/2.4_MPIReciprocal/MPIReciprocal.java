// Import MPJ Express MPI library
import mpi.*;

public class MPIReciprocal {
    public static void main(String[] args) throws Exception {

        // Initialize MPI environment — always first
        MPI.Init(args);

        // Get this process's unique rank
        int rank = MPI.COMM_WORLD.Rank();

        // Get total number of processes running
        int size = MPI.COMM_WORLD.Size();

        // Each process receives exactly 1 element
        int elementProcess = 1;

        // Root is process 0
        int root = 0;

        // The full array has exactly 'size' elements — one per process
        // Example: 4 processes → array has 4 elements
        double sendBuffer[] = new double[size];

        // Only root fills and prints the original array
        if (rank == root) {
            System.out.println("Root (Processor " + rank + ") initializing array of size " + size + "...");

            System.out.print("Original Array: [ ");
            for (int i = 0; i < size; i++) {
                // Values: 1.0, 2.0, 3.0, ... avoiding 0 to prevent division by zero
                sendBuffer[i] = (double)(i + 1);
                System.out.print(sendBuffer[i] + " ");
            }
            System.out.println("]\n");
        }

        // Each process receives 1 element from the array
        double receiveBuffer[] = new double[elementProcess];

        // Scatter sends exactly 1 element to each process
        MPI.COMM_WORLD.Scatter(
            sendBuffer,    0, elementProcess, MPI.DOUBLE,
            receiveBuffer, 0, elementProcess, MPI.DOUBLE,
            root
        );

        // The number this process received
        double receivedNumber = receiveBuffer[0];

        // Compute reciprocal: 1 divided by the received number
        // Example: received 4.0 → reciprocal = 1/4 = 0.25
        double reciprocal = 1.0 / receivedNumber;

        // Every process prints what it received and its computed reciprocal
        System.out.println("Processor " + rank + " received: " + receivedNumber +
                           "  ->  reciprocal = 1/" + (int)receivedNumber + " = " + reciprocal);

        // Wrap reciprocal in array for Gather
        double localResultArray[] = new double[1];
        localResultArray[0] = reciprocal;

        // gatherBuffer at root collects all reciprocals from all processes
        double gatherBuffer[] = new double[size];

        // Gather collects one reciprocal from every process into root's gatherBuffer
        MPI.COMM_WORLD.Gather(
            localResultArray, 0, elementProcess, MPI.DOUBLE,
            gatherBuffer,     0, elementProcess, MPI.DOUBLE,
            root
        );

        // Only root prints the final resultant array of all reciprocals
        if (rank == root) {
            System.out.println("\nGathering complete.");
            System.out.print("Root (Processor " + rank + ") Final Resultant Array (Reciprocals): [ ");
            for (int i = 0; i < size; i++) {
                // Print each reciprocal rounded to 3 decimal places
                System.out.printf("%.3f ", gatherBuffer[i]);
            }
            System.out.println("]");
        }

        // Finalize MPI — always last
        MPI.Finalize();
    }
}