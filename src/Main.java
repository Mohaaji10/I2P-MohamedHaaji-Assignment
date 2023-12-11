import java.util.Scanner;

public class Main {
    static int userInput;
    static boolean sessionActive = true;
    static TextInventory inventory = new TextInventory(); // Create an instance of TextInventory

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        ConsoleUI.displaymenu(); // Display the menu

        while (sessionActive) {
            userInput = MenuInputChoice(input); // Assign userInput based on user choice
            switch (userInput) {
                case 1 -> ConsoleUI.getuseriputadd(inventory); // Pass the TextInventory instance here
                case 2 -> ConsoleUI.getuseriputupdate(/* pass required parameters */);
                case 3 -> ConsoleUI.getuseriputremove(/* pass required parameters */);
                case 4 -> ConsoleUI.outputdailyreport(/* pass required parameters */);
                case 5 -> ConsoleUI.outputaredfile(inventory); // Pass the TextInventory instance here
                case 6 -> ConsoleUI.getuseriputsearch(); // Pass the TextInventory instance here
                case 7 -> sessionActive = false;
                default -> System.out.println("Unexpected error occurred, please enter an integer!");
            }
        }
    }

    private static int MenuInputChoice(Scanner input) {
        System.out.print("Enter your choice: ");
        return input.nextInt(); // Return the user input choice
    }
}

