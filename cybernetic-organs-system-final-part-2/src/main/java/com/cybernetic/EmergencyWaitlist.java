package com.cybernetic;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

public class EmergencyWaitlist {
    public String size() {
        return "";
    }

    public static class EmergencyCase implements Comparable<EmergencyCase> {
        private String caseId;
        private Patient patient;
        private int severityLevel;
        private LocalDateTime registrationTime;

        public EmergencyCase(String caseId, Patient patient, int severityLevel, LocalDateTime registrationTime) {
            this.caseId = caseId;
            this.patient = patient;
            this.severityLevel = severityLevel;
            this.registrationTime = registrationTime;
        }

        public String getCaseId() {
            return caseId;
        }

        public Patient getPatient() {
            return patient;
        }

        public int getSeverityLevel() {
            return severityLevel;
        }

        public LocalDateTime getRegistrationTime() {
            return registrationTime;
        }

        public void setSeverityLevel(int severityLevel) {
            this.severityLevel = severityLevel;
        }

        @Override
        public int compareTo(EmergencyCase other) {
            if (this.severityLevel != other.severityLevel) {
                return other.severityLevel - this.severityLevel; // Higher severity first
            }
            return this.registrationTime.compareTo(other.registrationTime); // Older cases first
        }
    }

    private PriorityQueue<EmergencyCase> priorityQueue;

    public EmergencyWaitlist() {
        priorityQueue = new PriorityQueue<>();
    }

    public void addEmergencyCase(String caseId, Patient patient, int severityLevel, LocalDateTime registrationTime) {
        priorityQueue.add(new EmergencyCase(caseId, patient, severityLevel, registrationTime));
    }

    public EmergencyCase getNextUrgentCase() {
        return priorityQueue.poll();
    }

    public boolean updateCaseSeverity(String caseId, int newSeverity) {
        PriorityQueue<EmergencyCase> tempQueue = new PriorityQueue<>();
        boolean found = false;

        while (!priorityQueue.isEmpty()) {
            EmergencyCase caseItem = priorityQueue.poll();
            if (caseItem.getCaseId().equals(caseId)) {
                caseItem.setSeverityLevel(newSeverity);
                found = true;
            }
            tempQueue.add(caseItem);
        }

        if (!found) {
            System.out.println("Case ID not found: " + caseId);
        }

        priorityQueue = tempQueue;
        return found;
    }

    public void displayAllCases() {
        for (EmergencyCase caseItem : priorityQueue) {
            System.out.printf("Case ID: %s, Severity: %d, Patient: %s%n",
                    caseItem.getCaseId(), caseItem.getSeverityLevel(), caseItem.getPatient().getName());
        }
    }
}
