package edu.bip.client.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.bip.client.Application;
import edu.bip.client.entity.BookEntity;
import edu.bip.client.utils.HTTPUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ApplicationController {

    public static String api = "http://localhost:2825/api/v1/book/";
    public static ObservableList<BookEntity> booksData = FXCollections.observableArrayList();
    static HTTPUtils http = new HTTPUtils();
    static Gson gson = new Gson();

    @FXML
    public TableView<BookEntity> tableBooks;

    @FXML
    private TableColumn<BookEntity, String> bookName;
    @FXML
    private TableColumn<BookEntity, String> bookAuthor;
    @FXML
    private TableColumn<BookEntity, String> bookPublisher;
    @FXML
    private TableColumn<BookEntity, String> bookYear;
    @FXML
    private TableColumn<BookEntity, String> bookChapter;

    @FXML
    private void initialize() throws Exception {
        getData();
        updateTable();
    }

    private void updateTable() throws Exception {
        bookName.setCellValueFactory(new PropertyValueFactory<BookEntity, String>("title"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<BookEntity, String>("author"));
        bookPublisher.setCellValueFactory(new PropertyValueFactory<BookEntity, String>("publishing"));
        bookYear.setCellValueFactory(new PropertyValueFactory<BookEntity, String>("year"));
        bookChapter.setCellValueFactory(new PropertyValueFactory<BookEntity, String>("kind"));
        tableBooks.setItems(booksData);
    }

    @FXML
    private void click_newBook() throws IOException {
        BookEntity tempBook = new BookEntity();
        booksData.add(tempBook);
        Application.showPersonEditDialog(tempBook, booksData.size()-1);
        addBook(tempBook);
    }

    @FXML
    private void click_removeBook() throws IOException {
        BookEntity selectedPerson = tableBooks.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            System.out.println(selectedPerson.getId());
            System.out.println(http.delete(api, selectedPerson.getId()));
            booksData.remove(selectedPerson);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Отсутсвует выбраный пользователь!");
            alert.setContentText("Пожалуйста, выберите пользователя из таблицы");
            alert.showAndWait();
        }
    }

    @FXML
    private void click_duplicateBook() throws IOException {
        BookEntity selectedPerson = tableBooks.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            addBook(selectedPerson);
            booksData.add(booksData.indexOf(selectedPerson)+1, selectedPerson);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Отсутсвует выбраный пользователь!");
            alert.setContentText("Пожалуйста, выберите пользователя из таблицы");
            alert.showAndWait();
        }
    }

    @FXML
    private void click_editBook() {
        BookEntity selectedPerson = tableBooks.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) Application.showPersonEditDialog(selectedPerson, booksData.indexOf(selectedPerson));
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Отсутсвует выбраный пользователь!");
            alert.setContentText("Пожалуйста, выберите пользователя из таблицы");
            alert.showAndWait();
        }
    }

    public static void getData() throws Exception {
        String res = http.get(api,"all");
        System.out.println(res);

        JsonObject base = gson.fromJson(res, JsonObject.class);
        JsonArray dataArr = base.getAsJsonArray("data");

        for (int i = 0; i < dataArr.size(); i++) {
            BookEntity newBook = gson.fromJson(dataArr.get(i).toString(), BookEntity.class);
            booksData.add(newBook);
        }
    }

    public static void addBook(BookEntity book) throws IOException {
        System.out.println(book.toString());
        book.setId(null);
        http.post(api+"add", gson.toJson(book).toString());
    }

    public static void updateBook(BookEntity book) throws IOException {
        http.put(api+"update", gson.toJson(book).toString());
    }
}