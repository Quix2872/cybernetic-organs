package com.cybernetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiagnosticDecisionTree {
    private DiagnosticNode root;
    private List<String> diagnosticPath;

    public DiagnosticDecisionTree() {
        this.diagnosticPath = new ArrayList<>();
    }

    public List<String> getDiagnosticPath() {
        return new ArrayList<>(diagnosticPath);
    }

    public void addDiagnosticCriteria(String measurementType, double threshold, String diagnosis) {
        DiagnosticNode newNode = new DiagnosticNode(measurementType, threshold);
        newNode.diagnosis = diagnosis;

        if (root == null) {
            root = newNode;
        } else {
            addNodeRecursive(root, newNode);
        }
    }

    private void addNodeRecursive(DiagnosticNode current, DiagnosticNode newNode) {
        if (newNode.thresholdValue < current.thresholdValue) {
            if (current.left == null) {
                current.left = newNode;
            } else {
                addNodeRecursive(current.left, newNode);
            }
        } else {
            if (current.right == null) {
                current.right = newNode;
            } else {
                addNodeRecursive(current.right, newNode);
            }
        }
    }

    public String diagnosePatient(Map<String, Double> measurements) {
        diagnosticPath.clear();
        return diagnosePatientRecursive(root, measurements, 1);
    }

    private String diagnosePatientRecursive(DiagnosticNode node, Map<String, Double> measurements, int level) {
        if (node == null) {
            return "Inconclusive";
        }

        diagnosticPath.add("Level " + level + ": " + node.measurementType + " = " +
                measurements.getOrDefault(node.measurementType, 0.0) + " " +
                (measurements.getOrDefault(node.measurementType, 0.0) < node.thresholdValue ? "<" : "≥") +
                " " + node.thresholdValue);

        if (node.diagnosis != null) {
            return node.diagnosis;
        }

        if (measurements.getOrDefault(node.measurementType, 0.0) < node.thresholdValue) {
            return diagnosePatientRecursive(node.left, measurements, level + 1);
        } else {
            return diagnosePatientRecursive(node.right, measurements, level + 1);
        }
    }

    public void printTree() {
        printTreeRec(root, "", true);
    }

    private void printTreeRec(DiagnosticNode node, String prefix, boolean isLeft) {
        if (node != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") +
                    node.measurementType + " (" + node.thresholdValue + ")" +
                    (node.diagnosis != null ? " -> " + node.diagnosis : ""));
            printTreeRec(node.left, prefix + (isLeft ? "│   " : "    "), true);
            printTreeRec(node.right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }
}
