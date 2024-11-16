package com.cybernetic;

import java.time.LocalDate;

public class Patient {
    private String id;
    private String name;
    private int age;
    private String bloodType;
    private String organNeeded;
    private int urgencyLevel;
    private LocalDate registrationDate;
    private String status;

    public Patient(String id, String name, int age, String bloodType, String organNeeded, int urgencyLevel,
                   LocalDate registrationDate, String status) {
        id = id.trim();
        name = name.trim();
        bloodType = bloodType.trim();
        organNeeded = organNeeded.trim();
        status = status.trim();

        // Validation
        if (!id.matches("PAT-\\d{4}")) throw new IllegalArgumentException("Invalid ID format.");
        if (age < 1 || age > 120) throw new IllegalArgumentException("Age must be between 1 and 120.");
        if (!bloodType.matches("A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-"))
            throw new IllegalArgumentException("Invalid blood type.");
        if (!organNeeded.matches("HEART|LUNG|KIDNEY|LIVER"))
            throw new IllegalArgumentException("Invalid organ type.");
        if (urgencyLevel < 1 || urgencyLevel > 10)
            throw new IllegalArgumentException("Urgency level must be between 1 and 10.");
        if (!status.equals("WAITING"))
            throw new IllegalArgumentException("New patients must have status 'WAITING'.");

        this.id = id;
        this.name = name;
        this.age = age;
        this.bloodType = bloodType;
        this.organNeeded = organNeeded;
        this.urgencyLevel = urgencyLevel;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getOrganNeeded() {
        return organNeeded;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getStatus() {
        return status;
    }
}
