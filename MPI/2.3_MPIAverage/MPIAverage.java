// Import Random class for generating random numbers
import java.util.Random;

// Import MPJ Express MPI library
import mpi.*;

public class MPIAverage {
    public static void main(String[] args) throws Exception {

        // Initialize MPI environment
        MPI.Init(args);

        // Get this process's rank (ID)
        int rank = MPI.COMM_WORLD.Rank();

        // Get total number of processes
        int size = MPI.COMM_WORLD.Size();

        // Each process will receive 5 random numbers to average
        int elementsPerProcess = 5;

        // Total random numbers = 5 per process * number of processes
        int totalElements = elementsPerProcess * size;

        // Root process is 0
        int root = 0;

        // Full array of random numbers — only root fills this
        double sendBuffer[] = new double[totalElements];

        // Only root generates and prints the random array
        if (rank == root) {
            System.out.println("Root (Processor " + rank + ") generating " + totalElements + " random numbers...");

            // Create a Random object to generate random numbers
            Random rand = new Random();

            System.out.print("Original Array: [ ");
            for (int i = 0; i < totalElements; i++) {
                // Generate random integer between 0 and 99
                sendBuffer[i] = rand.nextInt(100);
                System.out.print(sendBuffer[i] + " ");
            }
            System.out.println("]\n");
        }

        // Buffer to receive this process's chunk of numbers
        double receiveBuffer[] = new double[elementsPerProcess];

        // Scatter distributes the random array from root to all processes
        // Each process gets 'elementsPerProcess' double values
        MPI.COMM_WORLD.Scatter(
            sendBuffer,    0, elementsPerProcess, MPI.DOUBLE,
            receiveBuffer, 0, elementsPerProcess, MPI.DOUBLE,
            root
        );

        // Each process computes the sum of its received numbers
        double localSum = 0;
        for (int i = 0; i < elementsPerProcess; i++) {
            localSum += receiveBuffer[i];
        }

        // Each process computes its own local average
        double localAverage = localSum / elementsPerProcess;

        // Every process prints its local average
        System.out.println("Processor " + rank + " calculated local average: " + localAverage);

        // Wrap local average in array for MPI Gather
        double localAverageArr[] = new double[1];
        localAverageArr[0] = localAverage;

        // gatherBuffer at root will hold all local averages from every process
        double gatherBuffer[] = new double[size];

        // Gather collects one value from every process and puts them all at root
        // Unlike Reduce, Gather does NOT combine values — it just collects them
        MPI.COMM_WORLD.Gather(
            localAverageArr, 0, 1, MPI.DOUBLE,   // each process sends its average
            gatherBuffer,    0, 1, MPI.DOUBLE,   // root collects all averages here
            root
        );

        // Only root computes the final overall average from all gathered averages
        if (root == rank) {
            System.out.println("Gathering complete at root.");

            // Add up all the local averages gathered from all processes
            double totalSum = 0;
            for (int i = 0; i < size; i++) {
                System.out.println("  Average from Processor " + i + ": " + gatherBuffer[i]);
                totalSum += gatherBuffer[i];
            }

            // Divide by number of processes to get the final overall average
            double finalAverage = totalSum / size;
            System.out.println("Root (Processor " + rank + ") Final Overall Average: " + finalAverage);
        }

        // Finalize MPI
        MPI.Finalize();
    }
}