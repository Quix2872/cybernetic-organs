package com.cybernetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<Organ> getCompatibleOrgans(Patient patient) {
        return organs.stream()
                .filter(organ -> isCompatible(organ, patient))
                .collect(Collectors.toList());
    }

    private boolean isCompatible(Organ organ, Patient patient) {
        return calculateBloodTypeCompatibility(organ.getBloodType(), patient.getBloodType()) > 0 &&
                calculateWeightCompatibility(organ.getWeight(), patient.getWeight()) > 0 &&
                calculateHlaCompatibility(organ.getHlaType(), patient.getHlaType()) > 0;
    }

    public Map<Patient, List<Double>> calculateCompatibilityScores() {
        return patients.stream()
                .collect(Collectors.toMap(
                        patient -> patient,
                        patient -> organs.stream()
                                .map(organ -> calculateCompatibilityScore(organ, patient))
                                .collect(Collectors.toList())
                ));
    }

    double calculateCompatibilityScore(Organ organ, Patient patient) {
        double bloodTypeScore = calculateBloodTypeCompatibility(organ.getBloodType(), patient.getBloodType());
        double weightScore = calculateWeightCompatibility(organ.getWeight(), patient.getWeight());
        double hlaScore = calculateHlaCompatibility(organ.getHlaType(), patient.getHlaType());
        return (bloodTypeScore * 0.4) + (weightScore * 0.3) + (hlaScore * 0.3);
    }

    private int calculateBloodTypeCompatibility(String donorType, String recipientType) {
        // Example compatibility logic
        return donorType.equals(recipientType) ? 1 : 0;
    }

    private int calculateWeightCompatibility(int organWeight, int patientWeight) {
        // Example weight compatibility logic
        return Math.abs(organWeight - patientWeight) <= 50 ? 1 : 0;
    }

    private int calculateHlaCompatibility(String organHla, String patientHla) {
        // Example HLA compatibility logic
        return organHla.equals(patientHla) ? 1 : 0;
    }
}
