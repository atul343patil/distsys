// This file is used ONLY when running everything on one single machine
// It creates 3 nodes as separate threads, all communicating via localhost (127.0.0.1)

public class TokenRingSimulation {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Token Ring Mutual Exclusion Simulation ===");
        System.out.println("Running 3 nodes on a single machine using localhost\n");

        // Number of times each node will enter the critical section
        int rounds = 2;

        // Port each node listens on
        // Node 0: port 5000, Node 1: port 5001, Node 2: port 5002
        int[] ports = {5000, 5001, 5002};

        // All nodes are on the same machine so IP is always localhost
        String ip = "127.0.0.1";

        // Create 3 threads, one for each node
        Thread[] threads = new Thread[3];

        for (int i = 0; i < 3; i++) {
            final int nodeId     = i;
            final int listenPort = ports[i];
            // Next node's port — last node points back to first (ring structure)
            final int nextPort   = ports[(i + 1) % 3];
            // Only Node 0 starts with the token
            final boolean hasToken = (i == 0);

            // Create a thread that runs this node
            threads[i] = new Thread(() -> {
                Node node = new Node(nodeId, listenPort, ip, nextPort, hasToken, rounds);
                node.start();
            });

            // Give each thread a name for easier reading in output
            threads[i].setName("Node-" + i);
        }

        // IMPORTANT: Start receiving nodes BEFORE the token-holding node
        // Node 1 and Node 2 must be listening before Node 0 passes the token
        System.out.println("Starting Node 1 and Node 2 first (they need to listen first)...");
        threads[1].start(); // Start Node 1
        Thread.sleep(800);  // Wait 0.8 seconds
        threads[2].start(); // Start Node 2
        Thread.sleep(800);  // Wait 0.8 seconds

        System.out.println("Starting Node 0 (token holder)...\n");
        threads[0].start(); // Start Node 0 last — it has the token and will act immediately

        // Wait for all threads to finish before exiting
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\n=== Simulation Complete ===");
    }
}