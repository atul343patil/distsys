public class TokenRingSimulation {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Token Ring Mutual Exclusion Simulation ===");
        System.out.println("Running 3 nodes on a single machine using localhost\n");

        int rounds = 2;
        int[] ports = {5000, 5001, 5002};
        String ip = "127.0.0.1";

        Thread[] threads = new Thread[3];

        for (int i = 0; i < 3; i++) {
            final int nodeId     = i;
            final int listenPort = ports[i];
            final int nextPort   = ports[(i + 1) % 3];
            final boolean hasToken = (i == 0);

            threads[i] = new Thread(() -> {
                Node node = new Node(nodeId, listenPort, ip, nextPort, hasToken, rounds);
                node.start();
            });

            threads[i].setName("Node-" + i);
        }

        System.out.println("Starting Node 1 and Node 2 first (they need to listen first)...");
        threads[1].start(); 
        Thread.sleep(800);  
        threads[2].start(); 
        Thread.sleep(800);  

        System.out.println("Starting Node 0 (token holder)...\n");
        threads[0].start(); 

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\n=== Simulation Complete ===");
    }
}