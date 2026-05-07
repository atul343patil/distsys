import java.util.*;

public class Bully {

    static boolean[] alive;
    static int n;

    static void election(int p) {
        System.out.println("\n--> Process " + p + " initiates an ELECTION!");

        boolean higherResponded = false;

        // Step 1: Send ELECTION messages
        for (int i = p + 1; i < n; i++) {
            System.out.println("Message: Process " + p + " sends [ELECTION] to Process " + i);
        }

        // Step 2: Receive OK from higher alive processes
        for (int i = p + 1; i < n; i++) {
            if (alive[i]) {
                System.out.println("Message: Process " + i + " responds with [OK] to Process " + p);
                higherResponded = true;
            }
        }

        // Step 3: Higher process takes over
        if (higherResponded) {
            for (int i = p + 1; i < n; i++) {
                if (alive[i]) {
                    System.out.println("Process " + i + " takes over the election...");
                    election(i);
                    return;
                }
            }
        }

        // Step 4: Winner
        System.out.println("\n*** WINNER: Process " + p + " is the new COORDINATOR! ***");

        // Step 5: Inform all processes
        for (int i = 0; i < n; i++) {
            if (i != p && alive[i]) {
                System.out.println("Message: Process " + p + " sends [COORDINATOR] to Process " + i);
            }
        }

        // ⭐ ADDED FOR 6.3
        System.out.println("\nTime Complexity (Worst Case) = O(n^2)");
    }

    static void showStatus() {
        System.out.print("Status: ");
        for (int i = 0; i < n; i++) {
            System.out.print("P" + i + (alive[i] ? "(UP)  " : "(DOWN)  "));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter total number of processes: ");
        n = sc.nextInt();

        alive = new boolean[n];
        Arrays.fill(alive, true);

        int choice;

        System.out.println("\n========= MENU =========");
        System.out.println("1. UP a process");
        System.out.println("2. DOWN a process");
        System.out.println("3. ELECT leader");
        System.out.println("4. SHOW STATUS");
        System.out.println("5. EXIT");

        do {
            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Process to UP: ");
                    alive[sc.nextInt()] = true;
                    break;

                case 2:
                    System.out.print("Process to DOWN: ");
                    alive[sc.nextInt()] = false;
                    break;

                case 3:
                    System.out.print("Start election from process: ");
                    int p = sc.nextInt();

                    if (!alive[p]) {
                        System.out.println("Process is DOWN");
                    } else {
                        election(p);
                    }
                    break;

                case 4:
                    showStatus();
                    break;

                case 5:
                    System.out.println("Exit");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 5);

        sc.close();
    }
}