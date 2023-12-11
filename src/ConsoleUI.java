import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    static TextInventory inventory = new TextInventory();
    public static void displaymenu(){
        System.out.println("I N V E N T O R Y    M A N A G E M E N T    S Y S T E M");
        System.out.println("-----------------------------------------------");
        System.out.println("1. ADD NEW ITEM");
        System.out.println("2. UPDATE QUANTITY OF EXISTING ITEM");
        System.out.println("3. REMOVE ITEM");
        System.out.println("4. VIEW DAILY TRANSACTION REPORT");
        System.out.println("5. VIEW ITEMS");
        System.out.println("6. SEARCH ITEMS");
        System.out.println("---------------------------------");
        System.out.println("7. Exit");
    }

    /**
     -  The following outputs the items in the inventory stored in the TextInventory instance.
     - Retrieves the items list from the TextInventory instance provided.
     - If the items list is empty, it prints a message indicating there are no items in the inventory.
     - Otherwise, it displays the inventory items in the console.
     */

    public static void outputaredfile(TextInventory inventory) {
        // Access the itemsList from the TextInventory instance
        List<String[]> itemsList = inventory.getItemsList();

        if (itemsList.isEmpty()) {
            System.out.println("There are no items in the inventory.");
        } else {
            System.out.println("Inventory Items:");
            for (String[] item : itemsList) {
                for (String data : item) {
                    System.out.print(data + " ");
                }
                System.out.println();
            }
        }
    }

    /**
     * Here we take user input to add a new item to the inventory.
     * Asks for item description, quantity in stock, and unit price.
     * Calculates the total price based on quantity and price.
     * Calls the corresponding method from TextInventory to add the item.
     */

    public static void getuseriputadd(TextInventory inventory) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter item description:");
        String itemDescription = scanner.nextLine();

        System.out.println("Enter qtyInStock:");
        int qtyInStock = scanner.nextInt();

        System.out.println("Enter  unitPrice:");
        double unitPrice = scanner.nextDouble();


// Calculate totalPrice based on qtyInStock and unitPrice
        double totalPrice = qtyInStock * unitPrice;

// Call the method from TextInventory to add the item with calculated totalPrice
        ConsoleUI.inventory.addItem(itemDescription, qtyInStock, unitPrice, String.valueOf(totalPrice));
    }

    /**
     * The following takes user input to update an existing item in the inventory.
     * Asks for the ID of the item to update, new quantity, and new unit price.
     * Calls the corresponding method from TextInventory to update the item.
     */
    public static void getuseriputupdate() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the ID of the item to update:");
        String itemId = scanner.nextLine();

        System.out.println("Enter the new quantity:");
        int newQuantity = scanner.nextInt();

        System.out.println("Enter the new unit price:");
        double newUnitPrice = scanner.nextDouble();

        // Call the method from TextInventory to update the item
        inventory.updateanitem(itemId, newQuantity, newUnitPrice);
    }

    // The next two do similar things they take user input to remove an item based on the provided item ID and
    // Calls the corresponding method from TextInventory to remove the item.
    //
    public static void getuseriputremove() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the ID of the item to remove:");
        String itemId = scanner.nextLine();

        // Call the method from TextInventory to remove the item
        inventory.removeanitem(itemId);
    }

    public static void getuseriputsearch() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the ID of the item to search:");
        String itemId = scanner.nextLine();

        // Call the method from TextInventory to search for the item
        inventory.searchanitem(itemId);
    }


    public static void outputdailyreport(){}

}
