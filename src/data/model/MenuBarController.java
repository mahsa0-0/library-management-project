package data.model;

import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import util.LibraryManagmentUtil;

import java.io.IOException;

public class MenuBarController {

    // Handles the action when the "Issued List" option is clicked
    // Loads the issued list window and closes the current window
    public void handleIssuedList(ActionEvent e,StackPane rootPane) throws IOException {
        LibraryManagmentUtil.loadWindow(getClass().getResource("/ui/issuedList/issuedList.fxml"), "لیست امانتی ها", null);
        closeStage(rootPane);
    }

    // Handles the action when the "Add Book" option is clicked
    // Loads the "addBook.fxml" window and closes the current window
    public void handleAddBook(ActionEvent e, StackPane rootPane) throws IOException {
        // Load the "addBook.fxml" scene into the current window
        LibraryManagmentUtil.loadWindow(getClass().getResource("/ui/addBook/addBook.fxml"), "افزودن کتاب", null);

        // Close the current window after opening the new window
        closeStage(rootPane);
    }

    // Handles the action when the "Add Member" option is clicked
    // Loads the "addMember.fxml" window and closes the current window
    public void handleAddMember(ActionEvent e, StackPane rootPane) throws IOException {
        // Load the "addMember.fxml" scene into the current window
        LibraryManagmentUtil.loadWindow(getClass().getResource("/ui/addMember/addMember.fxml"), "افزودن دانشجو", null);

        // Close the current window after opening the new window
        closeStage(rootPane);
    }

    // Handles the action when the "Admin Page" option is clicked
    // Loads the "admin.fxml" window and closes the current window
    public void handleAdminPage(ActionEvent e, StackPane rootPane) throws IOException {
        // Load the "admin.fxml" scene into the current window
        LibraryManagmentUtil.loadWindow(getClass().getResource("/ui/admin/admin.fxml"), "Admin Page", null);

        // Close the current window after opening the new window
        closeStage(rootPane);
    }

    // Handles the action when the "Member List" option is clicked
    // Loads the "memberList.fxml" window and closes the current window
    public void handleMemberList(ActionEvent e, StackPane rootPane) throws IOException {
        // Load the "memberList.fxml" scene into the current window
        LibraryManagmentUtil.loadWindow(getClass().getResource("/ui/memberList/memberList.fxml"), "Member List", null);

        // Close the current window after opening the new window
        closeStage(rootPane);
    }

    // Private helper method to close the current window
    // This method hides the current window after the action is performed
    private void closeStage(StackPane root) {
        // Close the current window by hiding it
        root.getScene().getWindow().hide();
    }
}