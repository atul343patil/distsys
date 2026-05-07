// Import the MPJ Express MPI library — this gives access to MPI, MPI.COMM_WORLD, etc.
import mpi.*;

public class MPISum {
    public static void main(String[] args) throws Exception {

        // MPI.Init() MUST be the first MPI call — it initializes the MPI environment
        // args contains special MPI arguments passed by mpjrun
        MPI.Init(args);

        // Rank = the unique ID of this process (0 = root, 1, 2, 3... = workers)
        int rank = MPI.COMM_WORLD.Rank();

        // Size = total number of processes launched (set by -np flag when running)
        int size = MPI.COMM_WORLD.Size();

        // Each process will receive 'unitsize' number of elements
        int unitsize = 5;

        // Root process is process 0 — it holds the original array
        int root = 0;

        // Total elements in the array = elements per process * total processes
        // Example: if 4 processes and unitsize=5, total = 20 elements
        int total_elements = unitsize * size;

        // send_buffer is the full array — only root fills it, others keep it empty
        int send_buffer[] = new int[total_elements];

        // Only root process (rank 0) initializes the array
        if (root == rank) {
            System.out.println("Root (Processor " + rank + ") initializing array...");

            // Fill array with values 1, 2, 3, ... total_elements
            for (int i = 0; i < total_elements; i++) {
                send_buffer[i] = i + 1;
            }

            // Print the array so examiner can see the original data
            System.out.print("Array: [ ");
            for (int i = 0; i < total_elements; i++) {
                System.out.print(send_buffer[i] + " ");
            }
            System.out.println("]");
        }

        // receive_buffer holds the chunk of array that THIS process receives
        int receive_buffer[] = new int[unitsize];

        // Scatter divides send_buffer from root and sends each chunk to one process
        // Each process (including root) gets 'unitsize' elements
        // Arguments: send array, offset, count, type, receive array, offset, count, type, root
        MPI.COMM_WORLD.Scatter(
            send_buffer,    0, unitsize, MPI.INT,   // what to send (root side)
            receive_buffer, 0, unitsize, MPI.INT,   // where to receive (worker side)
            root                                     // who is doing the scatter
        );

        // Each process calculates the sum of its own received chunk
        int local_sum = 0;
        for (int i = 0; i < unitsize; i++) {
            local_sum += receive_buffer[i];
        }

        // Every process prints its intermediate sum
        System.out.println("Processor " + rank + " received chunk and calculated intermediate sum: " + local_sum);

        // Wrap local_sum in an array because MPI Reduce works with arrays
        int local_sum_arr[] = new int[1];
        local_sum_arr[0] = local_sum;

        // final_sum will hold the result after Reduce — only meaningful at root
        int final_sum[] = new int[1];

        // Reduce collects all local_sums from every process and ADDS them together
        // Result is stored in final_sum at root process
        // Arguments: send array, offset, receive array, offset, count, type, operation, root
        MPI.COMM_WORLD.Reduce(
            local_sum_arr, 0,   // each process sends its local sum
            final_sum,     0,   // root receives the combined result here
            1, MPI.INT,         // we are sending 1 integer
            MPI.SUM,            // the operation: add all values together
            root                // root collects the final result
        );

        // Only root prints the final total sum
        if (root == rank) {
            System.out.println("Root (Processor " + rank + ") Final Total Sum: " + final_sum[0]);
        }

        // MPI.Finalize() MUST be the last MPI call — it shuts down the MPI environment
        MPI.Finalize();
    }
}