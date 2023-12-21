import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextInventory {
    //
    private static List<String[]> itemsList = new ArrayList<>();
    private int itemIdCounter;
    public TextInventory() {
        // Initialize itemsList and transactionsList as needed
        itemsList = new ArrayList<>();
        this.loadExistingItems();
    }
    // Other methods...

    public List<String[]> getItemsList() {
        return itemsList;
    }
    public static class FileReadingExample {
        public static void readFile(String fileName, int expectedColumns) {
            List<String[]> list = new ArrayList<>();
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
            readFile("items.txt", 5);
            readFile("transactions.txt", 6);

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
                    } catch (NumberFormatException ignored) {
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
    // Method to add a transaction record to transactions.txt
    private void addTransactionRecord(String itemId, String description, String transactionType) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        String[] newTransactionData = new String[]{itemId, description, "0", "0","0", transactionType, dateFormat.format(date)};
        writeTransactionData(newTransactionData);
        System.out.println("Transaction added to the Daily Transaction Report.");
    }

    private void writeTransactionData(String[] transactionData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/resources/transactions.txt", true))) {
            String newTransaction = String.join(",", transactionData);
            writer.write(newTransaction + System.lineSeparator());
            System.out.println("Transaction added to the Daily Transaction Report.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    // Methods to add a new item to the inventory
    public  void addItem(String itemDescription, int qtyInStock, double unitPrice, String totalPrice ) {
        String itemId = generateID(); // Generate an ID for the new item
        // Calculate total price based on quantity in stock and unit price
        // Create an array for the new item's information
        String[] newItem = {itemId, itemDescription, String.valueOf(qtyInStock), String.valueOf(unitPrice), String.valueOf(totalPrice)};

        // Add the new item to the itemsList
        itemsList.add(newItem);

        // Output success message or perform additional actions as needed
        addTransactionRecord(itemId, "Item added", "added");

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
                System.out.println("Item updated successfully!");
                break;
            }
            addTransactionRecord(itemId, "Item updated", "updated");
        }
        System.out.println("Item with ID " + itemId + " not found!");
    }
    // Method to update item in the file
    public void removeanitem(String itemId) {
        for (int i = 0; i < itemsList.size(); i++) {
            String[] item = itemsList.get(i);
            if (item[0].equals(itemId)) {
                itemsList.remove(i);
                System.out.println("Item removed successfully!");
                break;
            }
        }
            addTransactionRecord(itemId, "Item removed", "removed");

    }
    // Method to remove item from the file
    public void searchanitem(String itemId) {
        for (String[] item : itemsList) {
            if (item[0].equals(itemId)) {
                System.out.println("Item found:");
                for (String data : item) {
                    System.out.print(data + " ");
                }
                System.out.println("Item Searched successfully!");
                return;
            }

            addTransactionRecord(itemId, "Item searched", "searched");
        }
    }
    public static void collectdailyreport(){
        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/transactions.txt"))) {
            String line;
            System.out.println("Daily Transaction Report:");
            while ((line = reader.readLine()) != null) {
                // Process each line of the transactions file
                System.out.println(line); // Print or process the transaction data as needed
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File 'transactions.txt' not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
