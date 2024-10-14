package com.cybernetic;

public class Main {

    public static void main(String[] args) {
        OrganCompatibilityAnalyzer inventory = new OrganCompatibilityAnalyzer();

        // Add sample organs
        inventory.addOrgan(new Organ("O1", "A+", 300, "1-2-3-4-5-6"));
        inventory.addOrgan(new Organ("O2", "O-", 280, "1-3-5-7-9-11"));

        // Add sample patients
        inventory.addPatient(new Patient("P1", "A+", 70, "1-2-3-4-5-6"));
        inventory.addPatient(new Patient("P2", "B-", 80, "2-4-6-8-10-12"));
        inventory.addPatient(new Patient("P3", "O+", 65, "1-3-5-7-9-11"));

        // Create and display the initial compatibility matrix
        int[][] compatibilityMatrix = inventory.createCompatibilityMatrix();
        inventory.displayMatrix(compatibilityMatrix);

        // Define and display the weight matrix
        double[] weights = {0.4, 0.3, 0.3}; // Blood type, Weight, HLA
        inventory.displayWeightMatrix(weights);

        // Calculate and display the final weighted compatibility matrix
        double[][] weightedMatrix = inventory.calculateWeightedCompatibility(weights);
        inventory.displayWeightedMatrix(weightedMatrix);

        // Provide explanations for the output
        System.out.println("\nIn this output:");
        System.out.println("- Rows represent organs (O1, O2, ...)");
        System.out.println("- Columns represent patients (P1, P2, ...)");
        System.out.println("- In the initial matrix, every 3 columns represent Blood Type (BT), Weight (WT), and HLA compatibility for each patient");
        System.out.println("- In the final matrix, each cell represents the overall weighted compatibility score between an organ and a patient");
        System.out.println("- The weighted compatibility score is calculated by multiplying each factor's score by its corresponding weight and summing the results\n");
    }
}
