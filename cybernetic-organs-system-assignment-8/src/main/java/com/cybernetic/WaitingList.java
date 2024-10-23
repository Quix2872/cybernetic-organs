package com.cybernetic;

public class WaitingList {
    private WaitingListNode head;

    public WaitingList() {
        this.head = null;
    }

    // Add a patient to the waiting list, maintaining the priority order
    public void addPatient(Patient patient, int priority) {
        WaitingListNode newNode = new WaitingListNode(patient, priority);

        if (head == null || priority > head.getPriority()) {
            newNode.setNext(head);
            head = newNode;
            return;
        }

        WaitingListNode current = head;
        while (current.getNext() != null && current.getNext().getPriority() >= priority) {
            current = current.getNext();
        }

        newNode.setNext(current.getNext());
        current.setNext(newNode);
    }

    // Remove and return the patient with the highest priority
    public Patient removeHighestPriority() {
        if (head == null) {
            return null;
        }

        Patient highestPriorityPatient = head.getPatient();
        head = head.getNext();
        return highestPriorityPatient;
    }

    // Update the priority of a patient
    public void updatePriority(String patientId, int newPriority) {
        WaitingListNode current = head;
        WaitingListNode previous = null;

        while (current != null && !current.getPatient().getId().equals(patientId)) {
            previous = current;
            current = current.getNext();
        }

        if (current == null) {
            System.out.println("Patient not found");
            return;
        }

        // Remove the patient and reinsert with new priority
        if (previous != null) {
            previous.setNext(current.getNext());
        } else {
            head = current.getNext();
        }

        addPatient(current.getPatient(), newPriority);
    }

    // Display the current waiting list
    public void displayWaitingList() {
        WaitingListNode current = head;
        int position = 1;

        if (current == null) {
            System.out.println("Waiting list is empty.");
            return;
        }

        while (current != null) {
            System.out.println(position + ". " + current.getPatient().getName() +
                    " (Priority: " + current.getPriority() + ")");
            current = current.getNext();
            position++;
        }
    }

    // Get the position of a patient in the waiting list
    public int getPosition(String patientId) {
        WaitingListNode current = head;
        int position = 1;

        while (current != null) {
            if (current.getPatient().getId().equals(patientId)) {
                return position;
            }
            current = current.getNext();
            position++;
        }

        return -1; // Patient not found
    }

    // Remove a patient from the waiting list by their ID
    public void removePatient(String patientId) {
        if (head == null) {
            return;
        }

        if (head.getPatient().getId().equals(patientId)) {
            head = head.getNext();
            return;
        }

        WaitingListNode current = head;
        while (current.getNext() != null && !current.getNext().getPatient().getId().equals(patientId)) {
            current = current.getNext();
        }

        if (current.getNext() != null) {
            current.setNext(current.getNext().getNext());
        }
    }

    // Getter for the head node
    public WaitingListNode getHead() {
        return head;
    }
}
