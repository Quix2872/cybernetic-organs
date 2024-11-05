package com.cybernetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CyberneticOrganCompatibility {
    private List<String> incompatibilityReasons;

    public CyberneticOrganCompatibility() {
        this.incompatibilityReasons = new ArrayList<>();
    }

    public boolean isCompatible(Patient patient, CyberneticOrgan organ, DiagnosticDecisionTree diagnosticTree) {
        incompatibilityReasons.clear();
        String diagnosticResult = diagnosticTree.diagnosePatient(patient.getAllMeasurements());

        if ("Not Compatible".equals(diagnosticResult)) {
            incompatibilityReasons.add("Diagnostic Tree Result: " + diagnosticResult);
            return false;
        }

        for (Map.Entry<String, CyberneticOrgan.Range> entry : organ.getRequirements().entrySet()) {
            String measurementType = entry.getKey();
            CyberneticOrgan.Range range = entry.getValue();
            double value = patient.getMeasurement(measurementType) != null ? patient.getMeasurement(measurementType) : Double.NaN;

            if (Double.isNaN(value) || value < range.min || value > range.max) {
                incompatibilityReasons.add(measurementType + " out of range: " +
                        String.format("%.2f", value) + " (required: " +
                        String.format("%.2f", range.min) + " - " + String.format("%.2f", range.max) + ")");
                return false;
            }
        }

        return true;
    }

    public List<String> getIncompatibilityReasons() {
        return new ArrayList<>(incompatibilityReasons);
    }
}
