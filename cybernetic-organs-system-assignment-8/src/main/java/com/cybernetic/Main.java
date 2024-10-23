package com.cybernetic;

public class Main {
    public static void main(String[] args) {
        try {
            // Your entire main logic goes here

            // Create a waiting list
            WaitingList waitingList = new WaitingList();

            // Create some patients
            Patient johnDoe = new Patient("P001", "John Doe", "A+", 70, "HLA-A");
            Patient janeSmith = new Patient("P002", "Jane Smith", "B-", 65, "HLA-B");
            Patient bobJohnson = new Patient("P003", "Bob Johnson", "O+", 80, "HLA-A");
            Patient aliceBrown = new Patient("P004", "Alice Brown", "AB-", 55, "HLA-C");

            // Add patients to the waiting list
            System.out.println("Adding patients to the waiting list...");
            waitingList.addPatient(johnDoe, 5);
            waitingList.addPatient(janeSmith, 3);
            waitingList.addPatient(bobJohnson, 4);

            // Display initial waiting list
            System.out.println("\nInitial Waiting List:");
            waitingList.displayWaitingList();

            // Add a new patient
            System.out.println("\nAdding new patient: Alice Brown (Priority: 6)");
            waitingList.addPatient(aliceBrown, 6);

            // Display updated waiting list
            System.out.println("Updated Waiting List:");
            waitingList.displayWaitingList();

            // Remove highest priority patient
            Patient removedPatient = waitingList.removeHighestPriority();
            System.out.println("\nRemoving highest priority patient: " + removedPatient.getName());

            // Update priority for Bob Johnson
            System.out.println("\nUpdating priority for Bob Johnson to 7");
            waitingList.updatePriority("P003", 7);

            // Display updated waiting list
            System.out.println("Updated Waiting List:");
            waitingList.displayWaitingList();

            // Create an organ
            Organ cyberHeart = new Organ("O001", "CyberHeart-X1", "A+", 350, "HLA-A");

            // Create an OrganCompatibilityAnalyzer
            OrganCompatibilityAnalyzer analyzer = new OrganCompatibilityAnalyzer();

            // Match organ to waiting list
            System.out.println("\nMatching " + cyberHeart.getName() + " to Waiting List:");
            Patient matchedPatient = analyzer.findCompatiblePatient(cyberHeart, waitingList);
            if (matchedPatient != null) {
                System.out.println("Compatible patient found: " + matchedPatient.getName() +
                        " (Priority: " + waitingList.getPosition(matchedPatient.getId()) + ")");
            } else {
                System.out.println("No compatible patient found in the waiting list.");
            }

            // After matching patient is found, remove them from the waiting list
            System.out.println("\nRemoving matched patient from the waiting list...");
            waitingList.removePatient(matchedPatient.getId());
            System.out.println("Updated Waiting List:");
            waitingList.displayWaitingList();

        } catch (Exception e) {
            e.printStackTrace();  // Print the full stack trace
        }
    }
}
