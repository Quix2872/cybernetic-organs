package com.cybernetic;

public class OrganCompatibilityAnalyzer {

    // Find the highest priority compatible patient for the organ
    public Patient findCompatiblePatient(Organ organ, WaitingList waitingList) {
        WaitingListNode current = waitingList.getHead();

        while (current != null) {
            Patient patient = current.getPatient();
            if (isCompatible(organ, patient)) {
                return patient; // Return the first compatible patient
            }
            current = current.getNext();
        }

        return null; // No compatible patient found
    }

    // Check if the organ is compatible with the patient
    private boolean isCompatible(Organ organ, Patient patient) {
        return calculateBloodTypeCompatibility(organ.getBloodType(), patient.getBloodType()) &&
                calculateWeightCompatibility(organ.getWeight(), patient.getWeight()) &&
                calculateHlaCompatibility(organ.getHlaType(), patient.getHlaType());
    }

    // Compatibility calculations (adjust as necessary for your assignment logic)
    private boolean calculateBloodTypeCompatibility(String donorType, String recipientType) {
        return donorType.equals(recipientType); // Example: exact match required
    }

    private boolean calculateWeightCompatibility(int organWeight, int patientWeight) {
        return Math.abs(organWeight - patientWeight) <= 50; // Example: within 50 weight units
    }

    private boolean calculateHlaCompatibility(String organHla, String patientHla) {
        return organHla.equals(patientHla); // Example: exact match required
    }
}
