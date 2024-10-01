package com.cybernetic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create an instance of OrganInventory and add organs
        System.out.println("Building organ inventory...");

        // Build the organ inventory from buildOrganInventory method then add the organs to the inventory
        List<Organ> organs = buildOrganInventory();
        OrganInventory inventory = new OrganInventory();
        for (Organ organ : organs) {
            inventory.addOrgan(organ);
        }

        System.out.println("Sorting inventory by name, model, and compatibility... Using Collections.sort()");
        long startTime = System.nanoTime();
        List<Organ> sortedOrgansBuiltIn = inventory.sortOrganByNameModelAndCompatibilityUsingBuiltInSort();
        long builtInSortTime = System.nanoTime() - startTime;
        System.out.println("Time taken to sort using Collections.sort(): " + builtInSortTime + " ns");

        System.out.println("Sorting inventory by name, model, and compatibility... Using QuickSort");
        startTime = System.nanoTime();
        List<Organ> sortedOrgansQuickSort = inventory.quickSortOrganByNameModelAndCompatibility(inventory.getOrganList());
        long quickSortTime = System.nanoTime() - startTime;
        System.out.println("Time taken to sort using QuickSort: " + quickSortTime + " ns");

        // Write the sorted inventory to the new CSV file
        writeOrganInventory(sortedOrgansQuickSort);

        System.out.println("Sorted inventory written to 'sorted-organ-list.csv'.");
    }

    private static void writeOrganInventory(List<Organ> sortedOrgans) {
        // Write the sorted inventory to a new CSV file
        String csvFile = "src/main/resources/sorted-organ-list.csv";
        try (PrintWriter writer = new PrintWriter(csvFile)) {
            writer.write("Name,Model,Functionality,Compatibility\n");
            for (Organ organ : sortedOrgans) {
                // Write in this order: name, model, functionality, compatibility
                writer.write(organ.getName() + "," + organ.getModel() + "," + organ.getFunctionality() + "," + organ.getCompatibility() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Organ> buildOrganInventory() {
        // Read the CSV file and build the organ inventory
        String csvFile = "src/main/resources/sample-organ-list.csv";
        String line;
        String cvsSplitBy = ",";
        List<Organ> inventory = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                // Use comma as separator
                String[] organData = line.split(cvsSplitBy);
                Organ newOrgan = new Organ(
                        organData[0].trim(), // Model
                        organData[1].trim(), // Name
                        organData[2].trim(), // Functionality
                        organData[3].trim()  // Compatibility
                );
                inventory.add(newOrgan);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inventory;
    }
}

