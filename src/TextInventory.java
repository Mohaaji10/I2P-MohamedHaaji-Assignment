import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class TextInventory {
    //
    private static List<String[]> itemsList = new ArrayList<>();
    private static List<String[]> transactionsList = new ArrayList<>();
    private int itemIdCounter;

    public TextInventory() {
        // Initialize itemsList and transactionsList as needed
        this.itemsList = new ArrayList<>();
        this.transactionsList = new ArrayList<>();
        this.loadExistingItems();
    }

    // Other methods...

    public List<String[]> getItemsList() {
        return itemsList;
    }

    public static class FileReadingExample {
        static List<String[]> itemsList = new ArrayList<>();
        static List<String[]> transactionsList = new ArrayList<>();
        private static final String ITEMS_FILE_PATH = "src/resources/items.txt";

        public static void readFile(String fileName, List<String[]> list, int expectedColumns) {
            try (Scanner scanner = new Scanner(new File(fileName))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] data = line.split(",");
                    if (data.length >= expectedColumns) {
                        list.add(data);
                    } else {
                        System.err.println("Error: Invalid data format in " + fileName);
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("Error: File '" + fileName + "' not found");
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            readFile("items.txt", itemsList, 5);
            readFile("transactions.txt", transactionsList, 6);

            // Use itemsList and transactionsList as needed
        }
    }

    private void loadExistingItems() {
        try (Scanner scanner = new Scanner(new File("src/resources/items.txt"))) {
            int maxId = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length >= 5) {
                    try {
                        int id = Integer.parseInt(data[0]);
                        if (id > maxId) {
                            maxId = id;
                        }
                        itemsList.add(data);
                    } catch (NumberFormatException e) {
                    }
                } else {
                    System.err.println("Error: Invalid data format in items file");
                }
            }
            itemIdCounter = maxId + 1; // Set the counter to the next available ID
        } catch (FileNotFoundException e) {
            System.err.println("Error: File 'items.txt' not found");
            e.printStackTrace();
        }
    }

    // Method to generate a 5-digit item ID
    public String generateID() {
        // Generate a 5-digit ID using the counter and pad it with leading zeros
        String itemId = String.format("%05d", itemIdCounter);
        itemIdCounter++; // Increment the counter for the next item
        return itemId;
    }



    // Methods to add a new item to the inventory
    public  void addItem(String itemDescription, int qtyInStock, double unitPrice, String totalPrice ) {
        String itemId = generateID(); // Generate an ID for the new item
        // Calculate total price based on quantity in stock and unit price
        double calculatedTotalPrice = qtyInStock * unitPrice;
        // Create an array for the new item's information
        String[] newItem = {itemId, itemDescription, String.valueOf(qtyInStock), String.valueOf(unitPrice), String.valueOf(totalPrice)};

        // Add the new item to the itemsList
        itemsList.add(newItem);

        // Output success message or perform additional actions as needed
        System.out.println("New item added successfully with ID: " + itemId);

        String newItemData = String.format("%s,%s,%.2f,%d,%s", itemId, itemDescription, unitPrice, qtyInStock, totalPrice);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/resources/items.txt", true))) {
            writer.write(newItemData + System.lineSeparator());
            System.out.println("Item added successfully!");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }




    public void updateanitem(String itemId, int newQuantity, double newUnitPrice) {
        for (String[] item : itemsList) {
            if (item[0].equals(itemId)) {
                // Update the quantity and unit price
                item[2] = String.valueOf(newQuantity);
                item[3] = String.valueOf(newUnitPrice);
                // Recalculate total price
                double totalPrice = newQuantity * newUnitPrice;
                item[4] = String.valueOf(totalPrice);

                // Update the file with the modified item
                updateItemInFile(item);
                System.out.println("Item updated successfully!");
                return;
            }
        }
        System.out.println("Item with ID " + itemId + " not found!");
    }

    // Method to update item in the file
    private void updateItemInFile(String[] item) {
        // Update the items.txt file with the modified item
        // Logic to update the file with the modified item information
    }




    public void removeanitem(String itemId) {
        for (String[] item : itemsList) {
            if (item[0].equals(itemId)) {
                itemsList.remove(item);
                // Remove the item from the file
                removeItemFromFile(itemId);
                System.out.println("Item removed successfully!");
                return;
            }
        }
        System.out.println("Item with ID " + itemId + " not found!");
    }

    // Method to remove item from the file
    private void removeItemFromFile(String itemId) {
        // Logic to remove the item from the items.txt file based on item ID
    }


    public void searchanitem(String itemId) {
        boolean found = false;
        for (String[] item : itemsList) {
            if (item[0].equals(itemId)) {
                found = true;
                System.out.println("Item found:");
                for (String data : item) {
                    System.out.print(data + " ");
                }
                System.out.println();
                break;
            }
        }
        if (!found) {
            System.out.println("Item with ID " + itemId + " not found!");
        }
    }

    public static void collectdailyreport(){

    }

}
