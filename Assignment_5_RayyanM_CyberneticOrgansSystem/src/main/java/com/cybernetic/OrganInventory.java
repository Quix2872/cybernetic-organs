package com.cybernetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrganInventory {
    private final List<Organ> inventory;

    public OrganInventory() {
        this.inventory = new ArrayList<>();
    }

    public void addOrgan(Organ organ) {
        inventory.add(organ);
    }

    public List<Organ> getOrganList() {
        return Collections.unmodifiableList(inventory);
    }

    // Ability to sort by multiple properties in order: name, model, compatibility using built-in sort
    public List<Organ> sortOrganByNameModelAndCompatibilityUsingBuiltInSort() {
        // Create a modifiable list from the inventory
        List<Organ> sortedList = new ArrayList<>(inventory);
        // Sort the list using Comparator chaining
        sortedList.sort(Comparator.comparing(Organ::getName)
                .thenComparing(Organ::getModel)
                .thenComparing(Organ::getCompatibility));
        return sortedList;
    }

    // Ability to sort by multiple properties in order: name, model, compatibility using QuickSort
    public List<Organ> quickSortOrganByNameModelAndCompatibility(List<Organ> unmodifiableOrganList) {
        // Create a modifiable list from the unmodifiableOrganList
        List<Organ> organList = new ArrayList<>(unmodifiableOrganList);
        quickSort(organList, 0, organList.size() - 1);
        return organList;
    }

    // QuickSort algorithm implementation
    private void quickSort(List<Organ> organList, int low, int high) {
        if (low < high) {
            int pi = partition(organList, low, high);
            quickSort(organList, low, pi - 1);
            quickSort(organList, pi + 1, high);
        }
    }

    // Partition method for QuickSort
    private int partition(List<Organ> organList, int low, int high) {
        Organ pivot = organList.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compareOrgan(organList.get(j), pivot) <= 0) {
                i++;
                Collections.swap(organList, i, j);
            }
        }
        Collections.swap(organList, i + 1, high);
        return i + 1;
    }

    // Comparison method for Organs based on name, model, and compatibility
    private int compareOrgan(Organ o1, Organ o2) {
        int nameCompare = o1.getName().compareTo(o2.getName());
        if (nameCompare != 0) {
            return nameCompare;
        }
        int modelCompare = o1.getModel().compareTo(o2.getModel());
        if (modelCompare != 0) {
            return modelCompare;
        }
        return o1.getCompatibility().compareTo(o2.getCompatibility());
    }
}
