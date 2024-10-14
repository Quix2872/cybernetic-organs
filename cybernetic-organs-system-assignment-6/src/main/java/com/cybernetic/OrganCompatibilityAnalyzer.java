package com.cybernetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrganCompatibilityAnalyzer {
    private List<Organ> organs;
    private List<Patient> patients;

    public OrganCompatibilityAnalyzer() {
        organs = new ArrayList<>();
        patients = new ArrayList<>();
    }

    public void addOrgan(Organ organ) {
        organs.add(organ);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    /**
     * Creates a compatibility matrix where each row represents an organ and each set of three columns represents
     * Blood Type (BT), Weight (WT), and HLA compatibility scores for each patient.
     *
     * @return A 2D integer array representing the compatibility matrix.
     */
    public int[][] createCompatibilityMatrix() {
        int[][] matrix = new int[organs.size()][patients.size() * 3]; // 3 factors: blood type, weight, HLA

        for (int i = 0; i < organs.size(); i++) {
            for (int j = 0; j < patients.size(); j++) {
                int bloodScore = calculateBloodTypeCompatibility(organs.get(i).getBloodType(), patients.get(j).getBloodType());
                int weightScore = calculateWeightCompatibility(organs.get(i).getWeight(), patients.get(j).getWeight());
                int hlaScore = calculateHlaCompatibility(organs.get(i).getHlaType(), patients.get(j).getHlaType());

                matrix[i][j * 3] = bloodScore;
                matrix[i][j * 3 + 1] = weightScore;
                matrix[i][j * 3 + 2] = hlaScore;
            }
        }

        return matrix;
    }

    /**
     * Calculates blood type compatibility based on exact matches, compatible matches (O donors), and incompatibility.
     *
     * @param donorType      Blood type of the donor organ.
     * @param recipientType  Blood type of the recipient patient.
     * @return Compatibility score for blood type.
     */
    private int calculateBloodTypeCompatibility(String donorType, String recipientType) {
        if (donorType.equals(recipientType)) {
            return 100;
        } else if (donorType.equals("O+") || donorType.equals("O-")) {
            return 80;
        }
        return 0;
    }

    /**
     * Calculates weight compatibility based on the ratio of organ weight to patient weight.
     *
     * The scoring thresholds are adjusted to produce scores like 100, 83, 67, 50, and 0 based on the ratio.
     *
     * @param organWeight     Weight of the organ in grams.
     * @param patientWeight   Weight of the patient in kilograms.
     * @return Compatibility score for weight.
     */
    private int calculateWeightCompatibility(int organWeight, int patientWeight) {
        double ratio = (double) organWeight / (patientWeight * 1000); // Organ weight in grams, patient weight in kg

        if (ratio >= 0.004 && ratio <= 0.006) {
            return 100;
        } else if (ratio >= 0.0035 && ratio < 0.004) {
            return 83;
        } else if (ratio >= 0.003 && ratio < 0.0035) {
            return 67;
        } else if (ratio >= 0.002 && ratio < 0.003) {
            return 50;
        }
        return 0;
    }

    /**
     * Calculates HLA compatibility as the percentage of matching HLA antigens.
     *
     * @param organHla        HLA type of the organ.
     * @param patientHla      HLA type of the patient.
     * @return Compatibility score for HLA.
     */
    private int calculateHlaCompatibility(String organHla, String patientHla) {
        Set<String> donorHlaSet = new HashSet<>(Arrays.asList(organHla.split("-")));
        Set<String> recipientHlaSet = new HashSet<>(Arrays.asList(patientHla.split("-")));

        // Find intersection
        Set<String> intersection = new HashSet<>(donorHlaSet);
        intersection.retainAll(recipientHlaSet);

        double matchPercentage = ((double) intersection.size() / donorHlaSet.size()) * 100;
        return (int) matchPercentage;
    }

    /**
     * Calculates the final weighted compatibility scores by combining individual compatibility scores with weights.
     *
     * @param weights An array of weights for blood type, weight, and HLA compatibility.
     * @return A 2D double array representing the final weighted compatibility matrix.
     */
    public double[][] calculateWeightedCompatibility(double[] weights) {
        int[][] compatibilityMatrix = createCompatibilityMatrix();
        double[][] resultMatrix = new double[organs.size()][patients.size()];

        for (int i = 0; i < organs.size(); i++) {
            for (int j = 0; j < patients.size(); j++) {
                double weightedScore = 0;
                weightedScore += compatibilityMatrix[i][j * 3] * weights[0];       // Blood Type
                weightedScore += compatibilityMatrix[i][j * 3 + 1] * weights[1];   // Weight
                weightedScore += compatibilityMatrix[i][j * 3 + 2] * weights[2];   // HLA
                resultMatrix[i][j] = weightedScore;
            }
        }

        return resultMatrix;
    }

    /**
     * Displays the initial compatibility matrix with clear labeling for each compatibility factor under each patient.
     *
     * @param matrix The compatibility matrix to display.
     */
    public void displayMatrix(int[][] matrix) {
        System.out.println("\nInitial Compatibility Matrix:");

        // Print header
        System.out.print("      ");
        for (Patient patient : patients) {
            System.out.printf("%-15s", patient.getId());
        }
        System.out.println();

        // Print sub-header for factors
        System.out.print("      ");
        for (int i = 0; i < patients.size(); i++) {
            System.out.print("BT    WT    HLA   ");
        }
        System.out.println();

        // Print matrix rows
        for (int i = 0; i < organs.size(); i++) {
            System.out.printf("%-5s ", organs.get(i).getName());
            for (int j = 0; j < patients.size(); j++) {
                System.out.printf("%-5d %-5d %-5d ",
                        matrix[i][j * 3],
                        matrix[i][j * 3 + 1],
                        matrix[i][j * 3 + 2]);
            }
            System.out.println();
        }
    }

    /**
     * Displays the weight factors used in the weighted compatibility calculation.
     *
     * @param weights The array of weights.
     */
    public void displayWeightMatrix(double[] weights) {
        System.out.println("\nWeight Matrix:");
        for (double weight : weights) {
            System.out.printf("%.1f  ", weight);
        }
        System.out.println();
    }

    /**
     * Displays the final weighted compatibility matrix with overall scores for each organ-patient pair.
     *
     * @param matrix The weighted compatibility matrix to display.
     */
    public void displayWeightedMatrix(double[][] matrix) {
        System.out.println("\nFinal Weighted Compatibility Matrix:");
        System.out.print("      ");
        for (Patient patient : patients) {
            System.out.printf("%-10s ", patient.getId());
        }
        System.out.println();
        for (int i = 0; i < organs.size(); i++) {
            System.out.printf("%-5s ", organs.get(i).getName());
            for (int j = 0; j < patients.size(); j++) {
                System.out.printf("%-10.1f ", matrix[i][j]);
            }
            System.out.println();
        }
    }
}
