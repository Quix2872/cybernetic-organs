package com.cybernetic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            OrganInventory inventory = new OrganInventory();
            ArrayList<String> validationErrors = new ArrayList<>();
            ArrayList<Patient> validPatients = new ArrayList<>();

            System.out.println("CyberOrgan Management System");
            System.out.println("============================\n");

            // Part 1: Loading and Validating Organs
            System.out.println("1. Loading and Validating Organs...");
            loadOrgans(inventory, validationErrors);

            System.out.println("\nOrgan Validation Errors:");
            for (int i = 0; i < Math.min(5, validationErrors.size()); i++) {
                System.out.println(validationErrors.get(i));
            }
            if (validationErrors.size() > 5) {
                System.out.printf("[...%d more validation errors...]\n", validationErrors.size() - 5);
            }

            // Demonstrating Sorting
            System.out.println("\n2. Demonstrating Organ Sorting...");
            System.out.println("Sorted by Power Level (Quicksort):");
            ArrayList<CyberneticOrgan> powerSorted = inventory.sortByPowerLevel();
            printTopFiveOrgans(powerSorted, organ ->
                    String.format("ID: %s, Power Level: %d (%s)", organ.getId(), organ.getPowerLevel(), organ.getType()));

            System.out.println("Sorted by Manufacture Date (Mergesort):");
            ArrayList<CyberneticOrgan> dateSorted = inventory.sortByManufactureDate();
            printTopFiveOrgans(dateSorted, organ ->
                    String.format("ID: %s, Date: %s (%s)", organ.getId(), organ.getManufactureDate(), organ.getType()));

            System.out.println("Sorted by Compatibility Score (Bubblesort):");
            ArrayList<CyberneticOrgan> compatibilitySorted = inventory.sortByCompatibilityScore();
            printTopFiveOrgans(compatibilitySorted, organ ->
                    String.format("ID: %s, Compatibility: %.2f (%s)", organ.getId(), organ.getCompatibilityScore(), organ.getType()));

            // Part 1: Loading and Validating Patients
            System.out.println("\n3. Loading and Validating Patients...");
            loadPatients(validPatients, validationErrors);

            System.out.println("\nPatient Validation Errors:");
            for (int i = 0; i < Math.min(5, validationErrors.size()); i++) {
                System.out.println(validationErrors.get(i));
            }
            if (validationErrors.size() > 5) {
                System.out.printf("[...%d more validation errors...]\n", validationErrors.size() - 5);
            }

            // Part 2: Transplant History Operations
            System.out.println("\n4. Transplant History Operations");
            TransplantHistory history = new TransplantHistory();
            history.addTransplantRecord("TR-001", "PAT-001", "ORG-001", LocalDateTime.now(), "Dr. Smith", "Success");
            history.addTransplantRecord("TR-002", "PAT-002", "ORG-002", LocalDateTime.now(), "Dr. Lee", "Pending");
            System.out.println("Recent Transplants:");
            history.getRecentTransplants(2);

            // Part 2: System Operations Log
            System.out.println("\n5. System Operations Log");
            SystemOperationsLog log = new SystemOperationsLog(10);
            log.pushOperation("MATCH-001", "MATCH", LocalDateTime.now(), "Matching organ to patient", true);
            log.pushOperation("TRANSPLANT-001", "TRANSPLANT", LocalDateTime.now(), "Scheduling transplant", true);
            log.pushOperation("EMERGENCY-001", "EMERGENCY", LocalDateTime.now(), "Emergency organ request", false);
            System.out.println("Recent Operations:");
            log.displayRecentOperations(3);

            System.out.println("Undoing last operation...");
            SystemOperationsLog.SystemOperation lastOperation = log.popLastOperation();
            System.out.printf("Popped Operation ID: %s, Type: %s, Description: %s%n",
                    lastOperation.getOperationId(), lastOperation.getOperationType(), lastOperation.getDescription());

            System.out.println("Current top operation:");
            SystemOperationsLog.SystemOperation topOperation = log.peekLastOperation();
            System.out.printf("Operation ID: %s, Type: %s, Description: %s%n",
                    topOperation.getOperationId(), topOperation.getOperationType(), topOperation.getDescription());

            // Part 2: Emergency Waitlist
            System.out.println("\n6. Emergency Waitlist");
            EmergencyWaitlist waitlist = new EmergencyWaitlist();

            // Dynamically add high-urgency patients to the emergency waitlist
            for (Patient patient : validPatients) {
                if (patient.getUrgencyLevel() >= 8) {
                    waitlist.addEmergencyCase("EMERG-" + (waitlist.size() + 1), patient, patient.getUrgencyLevel(), LocalDateTime.now());
                }
            }

            // Get and display the next urgent case
            System.out.println("Next urgent case:");
            EmergencyWaitlist.EmergencyCase urgentCase = waitlist.getNextUrgentCase();
            if (urgentCase != null) {
                System.out.printf("Case ID: %s, Severity: %d, Patient: %s%n",
                        urgentCase.getCaseId(), urgentCase.getSeverityLevel(), urgentCase.getPatient().getName());
            } else {
                System.out.println("No urgent cases available.");
            }

            // Update severity for an existing case
            System.out.println("Updating severity of case EMERG-002 to 5...");
            if (!waitlist.updateCaseSeverity("EMERG-002", 5)) {
                System.out.println("Case ID EMERG-002 not found.");
            }

            // Display all emergency cases in priority order
            System.out.println("All emergency cases:");
            waitlist.displayAllCases();


        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private static void loadOrgans(OrganInventory inventory, ArrayList<String> errors) {
        try (InputStream is = Main.class.getResourceAsStream("/organs.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) continue; // Skip comments
                String[] data = line.split(",");
                try {
                    CyberneticOrgan organ = new CyberneticOrgan(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            Integer.parseInt(data[3].trim()),
                            Double.parseDouble(data[4].trim()),
                            LocalDate.parse(data[5].trim()),
                            data[6].trim(),
                            data[7].trim()
                    );
                    inventory.addOrgan(organ);
                    System.out.println("Successfully added: " + organ.getId());
                } catch (IllegalArgumentException e) {
                    errors.add("Error with organ " + data[0].trim() + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading organs file: " + e.getMessage());
        }
    }

    private static void loadPatients(ArrayList<Patient> patients, ArrayList<String> errors) {
        try (InputStream is = Main.class.getResourceAsStream("/patients.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) continue; // Skip comments
                String[] data = line.split(",");
                try {
                    Patient patient = new Patient(
                            data[0].trim(),
                            data[1].trim(),
                            Integer.parseInt(data[2].trim()),
                            data[3].trim(),
                            data[4].trim(),
                            Integer.parseInt(data[5].trim()),
                            LocalDate.parse(data[6].trim()),
                            data[7].trim()
                    );
                    patients.add(patient);
                    System.out.println("Successfully validated: " + patient.getId());
                } catch (IllegalArgumentException e) {
                    errors.add("Error with patient " + data[0].trim() + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading patients file: " + e.getMessage());
        }
    }

    private static void printTopFiveOrgans(ArrayList<CyberneticOrgan> organs, java.util.function.Function<CyberneticOrgan, String> formatter) {
        for (int i = 0; i < Math.min(5, organs.size()); i++) {
            System.out.println(formatter.apply(organs.get(i)));
        }
        if (organs.size() > 5) {
            System.out.printf("[...%d more organs...]\n", organs.size() - 5);
        }
    }
}
