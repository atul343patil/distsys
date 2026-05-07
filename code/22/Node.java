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

        if (hasToken) {
            System.out.println("[Node " + nodeId + "] I have the initial token.");
            enterCriticalSection();
            rounds--;

            // If this node is already done after its first turn, send DONE signal
            if (rounds == 0) {
                System.out.println("[Node " + nodeId + "] All rounds complete. Sending DONE signal.");
                passSignal("DONE");
                return;
            }

            // Otherwise pass the token normally
            passSignal("TOKEN");
        }

        // Keep looping — wait for a signal, then decide what to do
        while (true) {
            String received = receiveSignal();

            // If the received signal is DONE, this node also shuts down
            // and forwards DONE to the next node so the shutdown propagates around the ring
            if ("DONE".equals(received)) {
                System.out.println("[Node " + nodeId + "] Received DONE signal. Shutting down.");

                // Forward DONE to next node so it also shuts down
                // (only if this node is not the last one that sent it)
                passSignal("DONE");
                return;
            }

            // Otherwise it is a TOKEN — enter critical section
            enterCriticalSection();
            rounds--;

            if (rounds == 0) {
                // This node is done — send DONE instead of TOKEN
                System.out.println("[Node " + nodeId + "] All rounds complete. Sending DONE signal.");
                passSignal("DONE");
                return;
            }

            // Still has more rounds — pass the token normally
            passSignal("TOKEN");
        }
    }

    private void enterCriticalSection() {
        System.out.println("\n[Node " + nodeId + "] >>> ENTERING Critical Section <<<");
        System.out.println("[Node " + nodeId + "] Executing critical task...");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[Node " + nodeId + "] <<< LEAVING Critical Section >>>\n");
    }

    // Renamed from passToken — now sends either "TOKEN" or "DONE"
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

    // Renamed from receiveToken — now returns whatever signal it receives ("TOKEN" or "DONE")
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