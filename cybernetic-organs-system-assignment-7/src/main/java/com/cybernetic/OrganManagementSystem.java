package com.cybernetic;

import java.util.*;
import java.util.stream.Collectors;

public class OrganManagementSystem {
    private List<Organ> organs;
    private List<Patient> patients;

    public OrganManagementSystem(List<Organ> organs, List<Patient> patients) {
        this.organs = organs;
        this.patients = patients;
    }

    public Set<String> getUniqueBloodTypes() {
        Set<String> organBloodTypes = organs.stream()
                .map(Organ::getBloodType)
                .collect(Collectors.toSet());
        Set<String> patientBloodTypes = patients.stream()
                .map(Patient::getBloodType)
                .collect(Collectors.toSet());
        organBloodTypes.addAll(patientBloodTypes);
        return organBloodTypes;
    }

    public Map<String, List<Patient>> groupPatientsByBloodType() {
        return patients.stream()
                .collect(Collectors.groupingBy(Patient::getBloodType));
    }

    public List<Organ> sortOrgansByWeight() {
        List<Organ> sortedOrgans = new ArrayList<>(organs);
        Collections.sort(sortedOrgans, Comparator.comparingInt(Organ::getWeight));
        return sortedOrgans;
    }

    public List<Organ> getTopCompatibleOrgans(Patient patient, int n) {
        OrganCompatibilityAnalyzer analyzer = new OrganCompatibilityAnalyzer();
        organs.forEach(analyzer::addOrgan);
        patients.forEach(analyzer::addPatient);

        return organs.stream()
                .sorted((o1, o2) -> Double.compare(
                        analyzer.calculateCompatibilityScore(o2, patient),
                        analyzer.calculateCompatibilityScore(o1, patient)))
                .limit(n)
                .collect(Collectors.toList());
    }
}
