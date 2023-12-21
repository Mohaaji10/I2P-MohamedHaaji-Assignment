package com.example.introtoprogrammingjavafxmh;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {

    public TableView itemTableView;
    public TableView transactionTableView;
    private Database database = new Database();
    public TextField itemDescriptionField;
    public TextField qtyField;
    public TextField unitPriceField;

    public TextField newQtyInStock;
    public TextField newUnitPrice;

    public TextField itemId;
    public TextField removeId;

    public TextField searchId;

    public Button addButton;
    public Button addupdateButton;
    public Button removeButton;

    public Button searchButton;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Inventory Management Organization");
        stage.setScene(scene);
        stage.show();
        database.setupConnection();
        database.createTransactionsTable();
        database.createItemsTable();
    }

    public static void main(String[] args) {
        launch();
    }

    @FXML
    private void initialize() {
        addButton.setOnAction(actionEvent -> addItem());
        addupdateButton.setOnAction(actionEvent -> updateItem());
        removeButton.setOnAction(actionEvent -> removeItem());
        searchButton.setOnAction(actionEvent -> searchItem());
        ViewItems();
        ViewTransactions();
        addTransactionRecord();
    }

    private void addTransactionRecord() {
    }



    private void addItem() {
        String itemDescription = itemDescriptionField.getText();
        int qtyInStock = Integer.parseInt(qtyField.getText());
        double unitPrice = Double.parseDouble(unitPriceField.getText());
        // Calculate total price based on quantity in stock and unit price
        double totalPrice = qtyInStock * unitPrice;
        database.addItem(itemDescription, qtyInStock, unitPrice, totalPrice);
        addTransactionRecord(itemDescription, "Item added", "added");
    }

    private void addTransactionRecord(String itemDescription, String itemAdded, String added) {
    }


    private void updateItem() {
        int itemIdValue = Integer.parseInt(itemId.getText());
        int newQtyInStockValue = Integer.parseInt(newQtyInStock.getText());
        double newUnitPriceValue = Double.parseDouble(newUnitPrice.getText());
        double newTotalPriceValue = newQtyInStockValue * newUnitPriceValue;
        database.updateItem(itemIdValue, newQtyInStockValue, newUnitPriceValue, newTotalPriceValue);
        addTransactionRecord(String.valueOf(itemIdValue), "Item updated", "updated");
    }

    private void removeItem() {
        int removeItemId = Integer.parseInt(removeId.getText());
            database.removeItem(removeItemId);
        addTransactionRecord(String.valueOf(removeItemId), "Item removed", "removed");
        }

    private void searchItem() {
        int searchItemId = Integer.parseInt(searchId.getText());
        database.searchItem(searchItemId);
        addTransactionRecord(String.valueOf(searchItemId), "Item searched", "searched");
    }

    private void ViewItems() {
        TableColumn<ObservableList<String>, String> id = new TableColumn<>("id");
        TableColumn<ObservableList<String>, String> description = new TableColumn<>("description");
        TableColumn<ObservableList<String>, String> unitPrice = new TableColumn<>("unit price");
        TableColumn<ObservableList<String>, String> qtyInStock = new TableColumn<>("quantity in stock");
        TableColumn<ObservableList<String>, String> totalPrice = new TableColumn<>("total price");

        itemTableView.getColumns().setAll(id, description, unitPrice, qtyInStock, totalPrice);

        id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        description.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        unitPrice.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        qtyInStock.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        totalPrice.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));

        itemTableView.setItems(database.getItemsContent());
    }

    private void ViewTransactions() {
        TableColumn<ObservableList<String>, String> transactionId = new TableColumn<>("Transaction ID");
        TableColumn<ObservableList<String>, String> description = new TableColumn<>("Description");
        TableColumn<ObservableList<String>, String> qtySold = new TableColumn<>("Qty Sold");
        TableColumn<ObservableList<String>, String> amount = new TableColumn<>("Amount");
        TableColumn<ObservableList<String>, String> stockRemaining = new TableColumn<>("Stock Remaining");
        TableColumn<ObservableList<String>, String> transactionType = new TableColumn<>("Transaction Type");
        TableColumn<ObservableList<String>, String> date = new TableColumn<>("Date");

        transactionTableView.getColumns().setAll(transactionId, description, qtySold, amount, stockRemaining, transactionType, date);

        transactionId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        description.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        qtySold.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        amount.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        stockRemaining.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        transactionType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(5)));
        date.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(6)));

        transactionTableView.setItems(database.getTransactionsContent());

    }



}

