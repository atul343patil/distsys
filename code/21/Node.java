import java.io.*;
import java.net.*;

public class Node {

    private int nodeId;
    private int listenPort;
    private String nextNodeIP;
    private int nextPort;
    private boolean hasToken;
    private int rounds;

    public Node(int nodeId, int listenPort, String nextNodeIP, int nextPort, boolean hasToken, int rounds) {
        this.nodeId     = nodeId;
        this.listenPort = listenPort;
        this.nextNodeIP = nextNodeIP;
        this.nextPort   = nextPort;
        this.hasToken   = hasToken;
        this.rounds     = rounds;
    }

    public void start() {
        // Flag to track if THIS node started the shutdown sequence
        boolean isDoneInitiator = false;

        if (hasToken) {
            System.out.println("[Node " + nodeId + "] I have the initial token.");
            enterCriticalSection();
            rounds--;

            if (rounds == 0) {
                System.out.println("[Node " + nodeId + "] All rounds complete. Initiating DONE signal.");
                isDoneInitiator = true;
                passSignal("DONE");
            } else {
                passSignal("TOKEN");
            }
        }

        while (true) {
            String received = receiveSignal();

            if ("DONE".equals(received)) {
                if (isDoneInitiator) {
                    // The DONE signal completed the full circle. Safe to shut down.
                    System.out.println("[Node " + nodeId + "] DONE signal completed the ring. Shutting down gracefully.");
                    return; 
                } else {
                    // Forward the DONE signal to the next node before shutting down
                    System.out.println("[Node " + nodeId + "] Received DONE signal. Forwarding and shutting down.");
                    passSignal("DONE");
                    return; 
                }
            }

            // Otherwise it is a TOKEN — enter critical section
            enterCriticalSection();
            rounds--;

            if (rounds == 0) {
                System.out.println("[Node " + nodeId + "] All rounds complete. Initiating DONE signal.");
                isDoneInitiator = true;
                passSignal("DONE");
            } else {
                passSignal("TOKEN");
            }
        }
    }

    private void enterCriticalSection() {
        System.out.println("\n[Node " + nodeId + "] >>> ENTERING Critical Section <<<");
        System.out.println("[Node " + nodeId + "] Executing critical task...");

        try {
            Thread.sleep(2000); // Simulate work in the critical section
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[Node " + nodeId + "] <<< LEAVING Critical Section >>>\n");
    }

    private void passSignal(String signal) {
        int attempts = 0;

        while (attempts < 10) {
            try {
                System.out.println("[Node " + nodeId + "] Sending " + signal + " to " + nextNodeIP + ":" + nextPort);

                Socket socket = new Socket(nextNodeIP, nextPort);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(signal);
                out.flush();
                socket.close();

                System.out.println("[Node " + nodeId + "] " + signal + " sent successfully.");
                return;

            } catch (IOException e) {
                attempts++;
                System.out.println("[Node " + nodeId + "] Next node not ready. Retrying (" + attempts + "/10)...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }

        System.out.println("[Node " + nodeId + "] ERROR: Could not send signal after 10 attempts.");
    }

    private String receiveSignal() {
        System.out.println("[Node " + nodeId + "] Waiting for signal on port " + listenPort + "...");

        try {
            ServerSocket serverSocket = new ServerSocket(listenPort);
            Socket socket = serverSocket.accept();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            String message = (String) in.readObject();

            socket.close();
            serverSocket.close();

            System.out.println("[Node " + nodeId + "] Received: " + message);
            return message;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        if (args.length != 6) {
            System.out.println("Usage: java Node <nodeId> <listenPort> <nextNodeIP> <nextPort> <hasToken(true/false)> <rounds>");
            return;
        }

        int     nodeId     = Integer.parseInt(args[0]);
        int     listenPort = Integer.parseInt(args[1]);
        String  nextNodeIP = args[2];
        int     nextPort   = Integer.parseInt(args[3]);
        boolean hasToken   = Boolean.parseBoolean(args[4]);
        int     rounds     = Integer.parseInt(args[5]);

        Node node = new Node(nodeId, listenPort, nextNodeIP, nextPort, hasToken, rounds);
        node.start();
    }
}