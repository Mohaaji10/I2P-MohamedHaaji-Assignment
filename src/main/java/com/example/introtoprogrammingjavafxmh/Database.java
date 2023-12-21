package com.example.introtoprogrammingjavafxmh;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet; // Import the ResultSet class

public class Database {

    String url = "jdbc:mysql://localhost:3306/inventorymanagementorganization";
    String username = "root";
    String password = "Hooyofc12!";
    Connection conn;
    public Database() {
        conn = getConnection();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void createItemsTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS items ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "description VARCHAR(50) NOT NULL,"
                + "unit_price DECIMAL(10,2) NOT NULL,"
                + "quantity INT NOT NULL,"
                + "total_price INT NOT NULL)";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableQuery);
            System.out.println("Items table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTransactionsTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS transactions ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "description VARCHAR(50) NOT NULL,"
                + "qtySold INT NOT NULL," // Adding the qtySold column
                + "amount INT NOT NULL,"
                + "stockRemaining INT NOT NULL,"
                + "transactionType VARCHAR(50) NOT NULL,"
                + "date DATE NOT NULL)";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableQuery);
            System.out.println("Transactions table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addItem(String itemDescription, int qtyInStock, double unitPrice, double totalPrice) {
        String insertQuery = "INSERT INTO items (description, unit_price, quantity, total_price) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);

            preparedStatement.setString(1, itemDescription);
            preparedStatement.setDouble(2, unitPrice);
            preparedStatement.setInt(3, qtyInStock);
            preparedStatement.setDouble(4, totalPrice);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Item added successfully!");
                addTransactionRecord(itemDescription, "Item added");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateItem(int itemId, int newQtyInStock, double newUnitPrice, double newTotalPrice) {
        String updateQuery = "UPDATE items SET quantity = ?, unit_price = ?, total_price = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);

            preparedStatement.setInt(1, newQtyInStock);
            preparedStatement.setDouble(2, newUnitPrice);
            preparedStatement.setDouble(3, newTotalPrice);
            preparedStatement.setInt(4, itemId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Item updated successfully!");
                addTransactionRecord(String.valueOf(itemId), "Item updated");
            } else {
                System.out.println("Item with ID " + itemId + " not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeItem(int itemId) {
        String deleteQuery = "DELETE FROM items WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);

            preparedStatement.setInt(1, itemId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Item with ID " + itemId + " removed successfully!");
                addTransactionRecord(String.valueOf(itemId), "Item removed");
            } else {
                System.out.println("Item with ID " + itemId + " not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTransactionRecord(String description, String transactionType) {
        String insertQuery = "INSERT INTO transactions (description, qtySold, amount, stockRemaining, transactionType, date) VALUES (?, ?, ?, ?, ?, NOW())";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);

            preparedStatement.setString(1, description);
            preparedStatement.setInt(2, 0); // Assuming qtySold is an INT column, set a default value (change as needed)
            preparedStatement.setInt(3, 0); // Set a default value for amount
            preparedStatement.setInt(4, 0); // Set a default value for stockRemaining
            preparedStatement.setString(5, transactionType);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Transaction recorded successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchItem(int itemId) {
        String searchQuery = "SELECT * FROM items WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(searchQuery);
            preparedStatement.setInt(1, itemId);

            ResultSet resultSet = preparedStatement.executeQuery(); // Use executeQuery() for SELECT queries

            if (resultSet.next()) {
                String description = resultSet.getString("description");
                double unitPrice = resultSet.getDouble("unit_price");
                int quantity = resultSet.getInt("quantity");
                double totalPrice = resultSet.getDouble("total_price");

                // Do something with the retrieved data
                System.out.println("Item with ID " + itemId + " found:");
                System.out.println("Description: " + description);
                System.out.println("Unit Price: " + unitPrice);
                System.out.println("Quantity: " + quantity);
                System.out.println("Total Price: " + totalPrice);
                addTransactionRecord(String.valueOf(itemId), "Item searched");
            } else {
                System.out.println("Item with ID " + itemId + " not found!");
                addTransactionRecord(String.valueOf(itemId), "Item searched");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public ObservableList<ObservableList<String>> getItemsContent() {
        String query = "SELECT id, description, unit_price, quantity, total_price FROM items";

        try (
                // Creating a PreparedStatement for the insert query
                PreparedStatement preparedStatement = conn.prepareStatement(query);
        ) {
            // Executing the insert query
            ResultSet result = preparedStatement.executeQuery();
            ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();
            while (result.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(result.getString("id"));
                row.add(result.getString("description"));
                row.add(result.getString("unit_price"));
                row.add(result.getString("quantity"));
                row.add(result.getString("total_price"));
                tableData.add(row);
            }
            return tableData;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<ObservableList<String>> getTransactionsContent() {
        String query = "SELECT id, description, qtySold, amount, stockRemaining, transactionType, date FROM transactions";

        try (
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                ) {

            ResultSet result = preparedStatement.executeQuery();
            ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();

            while (result.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(result.getString("id"));
                row.add(result.getString("description"));
                row.add(result.getString("qtySold"));
                row.add(result.getString("amount"));
                row.add(result.getString("stockRemaining"));
                row.add(result.getString("transactionType"));
                row.add(result.getString("date"));
                tableData.add(row);
            }

            return tableData;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




    public void setupConnection() {
        // Your code to set up the database connection
    }


}

