// Import MPJ Express MPI library
import mpi.*;

public class MPIMultiplication {
    public static void main(String[] args) throws Exception {

        // Initialize MPI — must be first MPI call
        MPI.Init(args);

        // Get this process's unique rank/ID
        int rank = MPI.COMM_WORLD.Rank();

        // Get total number of processes
        int size = MPI.COMM_WORLD.Size();

        // Each process will receive 4 elements
        int unitsize = 4;

        // Process 0 is root
        int root = 0;

        // Total array size = 4 elements per process * number of processes
        int total_elements = unitsize * size;

        // Full array — only root will fill it
        int send_buffer[] = new int[total_elements];

        // Only root initializes and prints the array
        if (rank == root) {
            System.out.println("Root (Processor " + rank + ") initializing array of size " + total_elements + "...");

            // Fill array with values cycling 1, 2, 3, 1, 2, 3, ... using modulo
            for (int i = 0; i < total_elements; i++) {
                send_buffer[i] = (i % 3) + 1;
            }

            // Print full array so examiner sees the data
            System.out.print("Array: [ ");
            for (int i = 0; i < total_elements; i++) {
                System.out.print(send_buffer[i] + " ");
            }
            System.out.println("]");
        }

        // Buffer where each process receives its portion of the array
        int receive_buffer[] = new int[unitsize];

        // Scatter splits the full array and distributes chunks to all processes
        MPI.COMM_WORLD.Scatter(
            send_buffer,    0, unitsize, MPI.INT,
            receive_buffer, 0, unitsize, MPI.INT,
            root
        );

        // Each process multiplies together all elements it received
        // Start with 1 because multiplying by 1 does not change the product
        int local_prod = 1;
        for (int i = 0; i < unitsize; i++) {
            local_prod *= receive_buffer[i];
        }

        // Each process prints its intermediate (partial) product
        System.out.println("Processor " + rank + " calculated intermediate multiplication: " + local_prod);

        // Wrap local product in an array for MPI Reduce
        int local_prod_arr[] = new int[2];
        local_prod_arr[0] = local_prod;

        // Array to receive the final product at root
        int final_prod[] = new int[2];

        // Reduce collects all local products and multiplies them together
        // MPI.PROD tells Reduce to multiply (not add) all values
        MPI.COMM_WORLD.Reduce(
            local_prod_arr, 0,
            final_prod,     0,
            1, MPI.INT,
            MPI.PROD,           // operation: multiply all local products together
            root
        );

        // Only root prints the final overall product
        if (rank == root) {
            System.out.println("Root (Processor " + rank + ") calculated final product: " + final_prod[0]);
        }

        // Finalize MPI — must be last MPI call
        MPI.Finalize();
    }
}