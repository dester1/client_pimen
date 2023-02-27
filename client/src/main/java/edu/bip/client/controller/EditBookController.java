package edu.bip.client.controller;

import edu.bip.client.entity.BookEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static edu.bip.client.controller.ApplicationController.booksData;
import static edu.bip.client.controller.ApplicationController.updateBook;


public class EditBookController {
    @FXML
    private TextField bookName_field;
    @FXML
    private TextField bookAuthor_field;
    @FXML
    private TextField bookPublisher_field;
    @FXML
    private TextField bookYear_field;
    @FXML
    private TextField bookChapter_field;

    private Stage editBookStage;
    private BookEntity book;
    private int bookID;
    private boolean okClicked = false;

    public void setDialogStage (Stage dialogStage) {this.editBookStage = dialogStage;}

    @FXML
    private void handleCancel() {editBookStage.close();}

    @FXML
    private void handleOk() throws IOException {
        if (isInputValid()) {
            book.setTitle(bookName_field.getText());
            book.setAuthor(bookAuthor_field.getText());
            book.setPublishing(bookPublisher_field.getText());
            book.setYear(bookYear_field.getText());
            book.setKind(bookChapter_field.getText());

            okClicked = true;
            editBookStage.close();
            booksData.set(bookID, book);
            updateBook(book);
        }
    }
    private boolean isInputValid() {
        String errorMessage = "";

        if (bookName_field.getText() == null || bookName_field.getText().length() == 0) errorMessage += "Не обнаружено название книги!\n";
        if (bookAuthor_field.getText() == null || bookAuthor_field.getText().length() == 0) errorMessage += "Не обнаружен автор книги!\n";
        if (bookPublisher_field.getText() == null || bookPublisher_field.getText().length() == 0) errorMessage += "Не обнаружено издание книги!\n";
        if (bookYear_field.getText() == null || bookYear_field.getText().length() == 0) errorMessage += "Не обнаружен год выпуска книги!\n";
        else {
            try {
                Integer.parseInt(bookYear_field.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Некорректное значение года выпуски книги (Должен быть целочисленным)!\n";
            }
        }
        if (bookChapter_field.getText() == null || bookChapter_field.getText().length() == 0) errorMessage += "\n";

        if (errorMessage.length() == 0) return true;
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(editBookStage);
            alert.setTitle("Error");
            alert.setHeaderText("Пожалуйста, укажите корректные значения текстовых полей");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }
    public void setLabels(BookEntity bookIn, int book_id) {
        this.book = bookIn;
        this.bookID = book_id;

        bookName_field.setText(book.getTitle());
        bookAuthor_field.setText(book.getAuthor());
        bookPublisher_field.setText(book.getPublishing());
        bookYear_field.setText(book.getYear());
        bookChapter_field.setText(book.getKind());
    }
    public boolean isOkClicked(){return okClicked;}
}
