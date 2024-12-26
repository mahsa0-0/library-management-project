package alertMaker;

import javafx.scene.control.Alert;

// The alertMaker class is designed to display various types of messages
// (error, information, success) using JavaFX dialogs.
public class alertMaker {
    public static void showErrorMessage(String title, String message) {

        // Create an alert of type ERROR
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void showMaterialDialog(String title, String message) {
        // Create an alert of type INFORMATION
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    public static void showSuccessMessage(String success, String book_returned_successfully) {
        // Create an alert of type INFORMATION
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(success);
        alert.setHeaderText(null);
        alert.setContentText(book_returned_successfully);
        alert.showAndWait();
    }
}
