package com.cybernetic;

import java.time.LocalDateTime;

public class TransplantHistory {
    private static class TransplantRecord {
        private String operationId;
        private String patientId;
        private String organId;
        private LocalDateTime timestamp;
        private String surgeon;
        private String outcome;
        private TransplantRecord next;

        public TransplantRecord(String operationId, String patientId, String organId,
                                LocalDateTime timestamp, String surgeon, String outcome) {
            this.operationId = operationId;
            this.patientId = patientId;
            this.organId = organId;
            this.timestamp = timestamp;
            this.surgeon = surgeon;
            this.outcome = outcome;
        }

        public String getOperationId() {
            return operationId;
        }

        public String getPatientId() {
            return patientId;
        }

        public String getOrganId() {
            return organId;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getSurgeon() {
            return surgeon;
        }

        public String getOutcome() {
            return outcome;
        }

        public TransplantRecord getNext() {
            return next;
        }

        public void setNext(TransplantRecord next) {
            this.next = next;
        }
    }

    private TransplantRecord head;
    private int size;

    public void addTransplantRecord(String operationId, String patientId, String organId,
                                    LocalDateTime timestamp, String surgeon, String outcome) {
        TransplantRecord newRecord = new TransplantRecord(operationId, patientId, organId, timestamp, surgeon, outcome);
        if (head == null) {
            head = newRecord;
        } else {
            TransplantRecord temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(newRecord);
        }
        size++;
    }

    public TransplantRecord findTransplantByPatient(String patientId) {
        TransplantRecord temp = head;
        while (temp != null) {
            if (temp.getPatientId().equals(patientId)) {
                return temp;
            }
            temp = temp.getNext();
        }
        return null;
    }

    public void getRecentTransplants(int count) {
        TransplantRecord temp = head;
        int index = size - count;
        while (temp != null && index-- > 0) {
            temp = temp.getNext();
        }
        while (temp != null) {
            System.out.printf("Operation ID: %s, Patient ID: %s, Outcome: %s%n",
                    temp.getOperationId(), temp.getPatientId(), temp.getOutcome());
            temp = temp.getNext();
        }
    }
}
