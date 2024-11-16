package com.cybernetic;

import java.time.LocalDate;

public class CyberneticOrgan {
    private String id;
    private String type;
    private String model;
    private int powerLevel;
    private double compatibilityScore;
    private LocalDate manufactureDate;
    private String status;
    private String manufacturer;

    public CyberneticOrgan(String id, String type, String model, int powerLevel, double compatibilityScore,
                           LocalDate manufactureDate, String status, String manufacturer) {
        // Validation
        if (!id.matches("ORG-\\d{4}")) throw new IllegalArgumentException("Invalid ID format.");
        if (!type.matches("HEART|LUNG|KIDNEY|LIVER")) throw new IllegalArgumentException("Invalid organ type.");
        if (!model.startsWith(type + "X-")) throw new IllegalArgumentException("Model must match organ type.");
        if (powerLevel < 1 || powerLevel > 100) throw new IllegalArgumentException("Power level must be 1-100.");
        if (compatibilityScore < 0.0 || compatibilityScore > 1.0)
            throw new IllegalArgumentException("Compatibility score must be between 0.0 and 1.0.");
        if (manufactureDate.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Manufacture date cannot be in the future.");
        if (!status.matches("AVAILABLE|ALLOCATED|DEFECTIVE"))
            throw new IllegalArgumentException("Invalid status value.");

        this.id = id;
        this.type = type;
        this.model = model;
        this.powerLevel = powerLevel;
        this.compatibilityScore = compatibilityScore;
        this.manufactureDate = manufactureDate;
        this.status = status;
        this.manufacturer = manufacturer;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public double getCompatibilityScore() {
        return compatibilityScore;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public String getStatus() {
        return status;
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
